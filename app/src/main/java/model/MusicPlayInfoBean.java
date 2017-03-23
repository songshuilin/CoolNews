package model;

/**
 * 音乐播放地址
 * Created by Administrator on 2016/12/23.
 */

public class MusicPlayInfoBean {

    /**
     * code : 0
     * status : success
     * msg : 数据请求成功
     * data : {"bitRate":128,"hash":"C23D025EE9ECE593ABD96D7B97DB97B4","fileName":"赵丽颖 - 十年 - 消音版伴奏","fileSize":3223345,"url":"http://song1.music.response.itmf.cn/94317a74c4398c516e8de02e1de0ac09/56f923ca/G044/M0A/01/01/DJQEAFYXd7SIfnevAAydc18Lb08AAAKhAGS18cADJ2L575.m4a","extName":"m4a","timeLength":201}
     */

    private int code;
    private String status;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * bitRate : 128
         * hash : C23D025EE9ECE593ABD96D7B97DB97B4
         * fileName : 赵丽颖 - 十年 - 消音版伴奏
         * fileSize : 3223345
         * url : http://song1.music.response.itmf.cn/94317a74c4398c516e8de02e1de0ac09/56f923ca/G044/M0A/01/01/DJQEAFYXd7SIfnevAAydc18Lb08AAAKhAGS18cADJ2L575.m4a
         * extName : m4a
         * timeLength : 201
         */

        private int bitRate;
        private String hash;
        private String fileName;
        private int fileSize;
        private String url;
        private String extName;
        private int timeLength;

        public int getBitRate() {
            return bitRate;
        }

        public void setBitRate(int bitRate) {
            this.bitRate = bitRate;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getExtName() {
            return extName;
        }

        public void setExtName(String extName) {
            this.extName = extName;
        }

        public int getTimeLength() {
            return timeLength;
        }

        public void setTimeLength(int timeLength) {
            this.timeLength = timeLength;
        }
    }
}
