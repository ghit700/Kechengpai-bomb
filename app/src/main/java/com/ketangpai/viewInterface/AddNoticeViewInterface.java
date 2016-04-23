package com.ketangpai.viewInterface;

import com.ketangpai.bean.Notice;

/**
 * Created by nan on 2016/4/22.
 */
public interface AddNoticeViewInterface {
    /**
     * 上传附件完成
     */
    void uploadAttachmentOnComplete(String fileUrl);

    /**
     * 显示文件上传的百分比
     * @param value
     */
    void onProgress(int value);

    /**
     * 发布公告
     */
    void publishNoticeOnComplete(Notice notice );

}
