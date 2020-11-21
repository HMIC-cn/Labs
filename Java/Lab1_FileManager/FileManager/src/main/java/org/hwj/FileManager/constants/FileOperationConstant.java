package org.hwj.FileManager.constants;

/**
 * 文件操作相关的常量
 * @author Hwj
 * @version 1.0
 */
public class FileOperationConstant {
    // 文件操作需要缓存时的大小
    public static final int BUFFER_LENGTH = 2048;

    // 一般文件操作提示信息
    public static final String FILE_NOT_FOUND_MESSAGE = "文件/文件夹不存在";
    public static final String FILE_EXIST_ALREADY = "文件/文件夹已存在";
    public static final String FILE_NOT_SELECT_MESSAGE = "未选中文件/文件夹";
    public static final String FILE_DIRECTORY_ENTER_NOT_PERMITTED = "文件夹禁止访问";
    public static final String FILE_NOT_CLEAR_ERROR_MESSAGE = "未知错误";

    // 文件拷贝相关提示信息
    public static final String FILE_DIRECTORY_SHOULD_CHOOSE_MESSAGE = "路径请选择文件夹而非文件";
    public static final String FILE_COPY_TO_SUB_BANNED_MESSAGE = "不能向子文件夹拷贝文件";
    public static final String FILE_COPY_SUCCESSES_MESSAGE = "文件/文件夹拷贝成功";
    public static final String FILE_COPY_FAIL_MESSAGE = "文件/文件夹拷贝失败";

    // 文件重命名提示信息
    public static final String FILE_INPUT_NEW_DIRECTORY_NAME_MESSAGE = "请输入文件/文件夹名称";
    public static final String FILE_RENAME_CANCEL_MESSAGE = "重命名操作已取消";

    // 文件删除提示信息
    public static final String FILE_DELETE_CANCEL_MESSAGE = "删除操作已取消";

    // 文件压缩解压提示信息
    public static final String FILE_SHOULD_CHOOSE_ZIP_MESSAGE = "应选择压缩文件";
    public static final String FILE_ZIP_SUCCESS_MESSAGE = "压缩完成";
    public static final String FILE_UNZIP_SUCCESS_MESSAGE = "解压完成";

    // 文件加密解密提示信息
    public static final String FILE_SHOULD_SELECT_FILE_MESSAGE = "应选择文件进行加密/解密";
    public static final String FILE_SHOULD_SELECT_ENCRYPTED_MESSAGE = "应选择已加密文件进行解密";
    public static final String FILE_INPUT_YOUR_PASSWORD_MESSAGE = "请输入密码";
    public static final String FILE_GET_WRONG_PASSWORD_MESSAGE = "密码错误";
    public static final String FILE_ENCRYPTED_DONE_MESSAGE = "文件已加密";
    public static final String FILE_DECRYPTED_DONE_MESSAGE = "文件已解密";
    public static final String FILE_DECRYPTED_FAIL_MESSAGE = "解密失败";

    // 进度缩小倍数
    public static final int FILE_PROGRESS_SMALL_TIMES = 100;
}
