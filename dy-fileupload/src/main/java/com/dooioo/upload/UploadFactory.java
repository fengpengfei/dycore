package com.dooioo.upload;

import com.dooioo.upload.exception.UploadException;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.uploads.DocUpload;
import com.dooioo.upload.uploads.ImageUpload;
import com.dooioo.upload.uploads.RecordUpload;
import com.dooioo.upload.uploads.ZipFileUpload;
import com.dooioo.upload.utils.FileUtils;
import org.apache.commons.fileupload.FileItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA at 13-6-25 上午10:28.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class UploadFactory {
    private static Set<String> RECORD_TYPE = new HashSet<String>();
    private static Set<String> ZIP_TYPE = new HashSet<String>();

    static {
        //录音文件
        RECORD_TYPE.add("wav");

        //压缩文件
        ZIP_TYPE.add("zip");
        ZIP_TYPE.add("rar");
    }

    /**
     * 上传图片
     * //TODO:目前只支持同步压缩 异步调用
     * @param fileItem
     * @param imageArgConverts
     * @return
     * @throws UploadException
     */
    public static UploadResult uploadPic(FileItem fileItem, ImageArgConvert... imageArgConverts) throws Exception {
        return uploadPic(fileItem.get(), fileItem.getName(), imageArgConverts);
    }

    /**
     * 上传图片
     * //TODO:目前只支持同步压缩 异步调用未实现
     * @param data
     * @param imageArgConverts
     * @return
     * @throws UploadException
     */
    public static UploadResult uploadPic(byte[] data, String fileName,  ImageArgConvert... imageArgConverts) throws Exception {
        return ImageUpload.upload(data, fileName, imageArgConverts);
    }

    /**
     * 上传文档文件 eg:wav.doc
     * @param fileItem
     * @param isunzip 如果是zip文件是否需要解压
     * @return
     * @throws UploadException
     */
    public static UploadResult upload(FileItem fileItem,boolean isunzip) throws UploadException {
        return upload(fileItem.get(), fileItem.getName(),isunzip);
    }

    /**
     * 上传文档文件 eg:wav.doc
     * @param data
     * @param fileName
     * @param isunzip  如果是zip文件是否需要解压
     * @return
     * @throws UploadException
     */
    public static UploadResult upload(byte[] data, String fileName, boolean isunzip) throws UploadException {
        String extName = FileUtils.getFileExtName(fileName).toLowerCase();
        if (RECORD_TYPE.contains(extName)) {
            return RecordUpload.upload(data, fileName);
        }
        if (ZIP_TYPE.contains(extName)) {
            return ZipFileUpload.upload(data, fileName, isunzip);
        }
        return DocUpload.upload(data, fileName);
    }

    /**
     * 异步生成缩略图
     *
     * @param picPath          图片路径
     * @param imageArgConverts 缩略图尺寸
     * @throws UploadException
     */
    public static void asyncGeneratePics(String picPath, List<ImageArgConvert> imageArgConverts) throws Exception {
        if (imageArgConverts == null || imageArgConverts.size() == 0)
            return;
        ImageUpload.scaleMultiHandle(picPath, imageArgConverts);
    }
}
