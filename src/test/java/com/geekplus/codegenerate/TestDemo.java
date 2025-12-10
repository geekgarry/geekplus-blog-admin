package com.geekplus.codegenerate;

import com.geekplus.common.domain.ChatPrompt;
import com.geekplus.common.util.string.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestDemo {

	public static void main(String[] args) {
		String testJson="[{\"res\":\"43543546\",\"msg\":\"grgdf\"},{\"res\":\"123456\",\"msg\":\"fds\"},{\"res\":\"987654\",\"msg\":\"jhgg\"}]";
		Date date = new Date();
		SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		SimpleDateFormat hongkongSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		hongkongSdf.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));
//		System.out.println("毫秒数:" + date.getTime() + ", 北京时间:" + bjSdf.format(date));
//		System.out.println("毫秒数:" + date.getTime() + ", 香港时间:" + hongkongSdf.format(date));
//		System.out.println(date);
		//System.out.println(jsonOb.get(jsonOb.size()-1));
		ChatPrompt chatPrompt=new ChatPrompt();
		Object obj1 = " ";
		// 输出添加后的JSONObject
		System.out.println(ObjectUtils.isEmpty(chatPrompt.getMediaData()));
		System.out.println(StringUtils.isEmpty(obj1.toString()));
		System.out.println(chatPrompt.getMediaData());
	}

	public void randomNum(){
		int[]arr=new int[20];
		Random suiji=new Random();


		for(int i=0;i<arr.length;i++)
		{        int num=suiji.nextInt(100)+1;
			arr[i]=num;
			for(int j=1;j<=i;j++)
			{
				if(arr[j]==arr[i])
					break;

			}
			System.out.println(arr[i]);
		}
	}
}
