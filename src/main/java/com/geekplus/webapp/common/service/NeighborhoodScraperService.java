package com.geekplus.webapp.common.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * author     : geekplus
 * email      :
 * date       : 10/31/25 9:41 AM
 * description: //TODO
 */
public class NeighborhoodScraperService {
    private static final Logger logger = LoggerFactory.getLogger(NeighborhoodScraperService.class);

    // 贝壳找房小区搜索URL模板
    // 注意：城市域名 {city} 和 搜索关键词 {keyword}
    private static final String KE_COM_SEARCH_URL_TEMPLATE = "https://%s.ke.com/xiaoqu/rs%s/";

    // 模拟浏览器用户代理
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";

    /**
     * 根据小区名称和城市从贝壳找房爬取小区信息
     *
     * @param neighborhoodName 小区名称
     * @param city             城市拼音，如 "bj", "sh", "gz"
     * @return 匹配的小区信息列表
     */
    public List<NeighborhoodDTO> searchNeighborhoods(String neighborhoodName, String city) {
        List<NeighborhoodDTO> results = new ArrayList<>();
        if (neighborhoodName == null || neighborhoodName.trim().isEmpty() || city == null || city.trim().isEmpty()) {
            return results;
        }

        try {
            // URL编码搜索词
            String encodedName = URLEncoder.encode(neighborhoodName, StandardCharsets.UTF_8.name());
            String url = String.format(KE_COM_SEARCH_URL_TEMPLATE, city.toLowerCase(), encodedName);
            logger.info("Attempting to scrape URL: {}", url);

            // 使用Jsoup连接并获取HTML文档
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(15 * 1000) // 设置更长的超时时间
                    .get();

            // ----------------------------------------------------
            // 以下是针对贝壳找房 HTML 结构的 CSS 选择器，非常重要！
            // 请务必通过浏览器开发者工具检查贝壳找房当前页面的实际DOM结构，
            // 这些选择器可能会随着网站更新而失效。
            // ----------------------------------------------------

            // 查找小区列表的容器，通常是 ul 标签
            Elements communityList = doc.select("ul.listContent > li.xiaoquListItem");

            if (communityList.isEmpty()) {
                logger.warn("No direct search results found for '{}' in {} on ke.com using current Jsoup selectors. Returning simulated data.", neighborhoodName, city);
                // 如果没有找到结果，或者因为反爬，返回模拟数据
                if (neighborhoodName.contains("测试")) {
                    results.add(new NeighborhoodDTO("测试小区贝壳1号", city + "市海淀区模拟地址", "50000元/平", "http://test.ke.com/xiaoqu/123", url));
                    results.add(new NeighborhoodDTO("测试小区贝壳2号", city + "市朝阳区模拟地址", "45000元/平", "http://test.ke.com/xiaoqu/456", url));
                } else {
                    results.add(new NeighborhoodDTO(neighborhoodName + " (模拟数据)", city + "市模拟地址", "暂无", "http://test.ke.com/xiaoqu/simulated", url));
                }
                return results;
            }

            for (Element item : communityList) {
                String name = item.select("div.title a").text(); // 小区名称通常在 div.title 下的 a 标签
                String address = item.select("div.positionInfo a").first() != null ? item.select("div.positionInfo a").first().text() : ""; // 地址在 div.positionInfo 下的 a 标签
                String averagePrice = item.select("div.xiaoquListItemRight div.totalPrice span.totalPrice").text(); // 平均价格在特定的 div 结构下
                String detailUrl = item.select("div.title a").attr("href"); // 小区详情页链接

                if (!name.isEmpty()) {
                    results.add(new NeighborhoodDTO(name, address, averagePrice + "元/平", detailUrl, url));
                }
            }
            logger.info("Found {} results for '{}' in {} from ke.com", results.size(), neighborhoodName, city);

        } catch (IOException e) {
            logger.error("Error scraping neighborhood from ke.com: {}-{}", city, neighborhoodName, e);
            // 爬取失败时，也返回模拟数据
            results.add(new NeighborhoodDTO(neighborhoodName + " (爬取失败，返回模拟数据)", city + "市爬取失败地址", "N/A", "error-url", "error-source-url"));
        }
        return results;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class NeighborhoodDTO {
        private String name;
        private String address;
        private String averagePrice; // 新增：平均价格
        private String detailUrl;    // 新增：小区的详情页链接
        private String sourceUrl;    // 搜索结果页来源
    }

    public static void main(String[] args) {
        NeighborhoodScraperService service = new NeighborhoodScraperService();
        System.out.println(service.searchNeighborhoods("山水园", "sh"));
    }
}
