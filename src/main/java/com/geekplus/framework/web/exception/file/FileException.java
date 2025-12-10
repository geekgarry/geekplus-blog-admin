package com.geekplus.framework.web.exception.file;

import com.geekplus.framework.web.exception.BaseException;

/**
 * 文件信息异常类
 *
 * @author
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
