package model;

import java.util.List;

/**
 * Created by Administrator on 2016/12/23.
 */

public class MusicQueryBean {

    /**
     * code : 0
     * status : success
     * msg : 数据请求成功
     * data : {"current_page":1,"keyword":"十年","total_rows":480,"total_page":32,"page_size":15,"data":[{"filename":"陈奕迅 - 十年","extname":"mp3","m4afilesize":851201,"filesize":3242822,"bitrate":128,"isnew":0,"duration":202,"album_name":"黑白灰","singername":"陈奕迅","hash":"936051ea140e3cfacb629d2bfaf430d7"},{"filename":"赵丽颖 - 十年【《我们的十年》 电影主题曲】","extname":"mp3","m4afilesize":832156,"filesize":3328348,"bitrate":132,"isnew":0,"duration":201,"album_name":"我们的十年 电影原声带","singername":"赵丽颖","hash":"bc1bf186645db2dc11cb77b53e3d50fa"},{"filename":"韩红、陈奕迅 - 十年","extname":"mp3","m4afilesize":1259429,"filesize":4911226,"bitrate":128,"isnew":0,"duration":307,"album_name":"我是歌手第三季合辑","singername":"韩红、陈奕迅","hash":"7c00d6e8a457ce6ee8a558d37fe37aa3"},{"filename":"TFBOYS - 十年","extname":"mp3","m4afilesize":938719,"filesize":3670644,"bitrate":128,"isnew":0,"duration":229,"album_name":"","singername":"TFBOYS","hash":"b30f03df9b3247118eeef5796e25551a"},{"filename":"南妮 - 十年","extname":"mp3","m4afilesize":816102,"filesize":3175768,"bitrate":129,"isnew":0,"duration":198,"album_name":"唱醉你","singername":"南妮","hash":"5ad184bcdfc3894c1fba8ecd0e689e74"},{"filename":"陈奕迅 - 十年 - 伴奏版","extname":"mp3","m4afilesize":864278,"filesize":3296990,"bitrate":128,"isnew":0,"duration":206,"album_name":"","singername":"陈奕迅","hash":"07c1bb47ef1a3ce3c24196a2aafaa6a2"},{"filename":"陈翔 - 十年","extname":"mp3","m4afilesize":835583,"filesize":3251866,"bitrate":128,"isnew":0,"duration":203,"album_name":"","singername":"陈翔","hash":"2397a8932678d932242de9e8853c2d20"},{"filename":"刘德华 - 十年 - 香港Unforgettable Concert","extname":"mp3","m4afilesize":1175158,"filesize":4511694,"bitrate":128,"isnew":0,"duration":279,"album_name":"","singername":"刘德华","hash":"2e0714a12dc974035c9fca28d57352b8"},{"filename":"王啸坤 - 十年【《最佳前男友》 电视剧插曲】","extname":"mp3","m4afilesize":828362,"filesize":3283967,"bitrate":130,"isnew":0,"duration":201,"album_name":"最佳前男友 电视剧原声带","singername":"王啸坤","hash":"76e3662c0ff2692817985e0e263292b7"},{"filename":"刘若英 - 十年 - 铃声版","extname":"mp3","m4afilesize":150171,"filesize":573149,"bitrate":128,"isnew":0,"duration":36,"album_name":"","singername":"刘若英","hash":"b7a980ebe4a5c2737c5845da35dc5f51"},{"filename":"陈奕迅 - 十年 - 原版伴奏","extname":"mp3","m4afilesize":852411,"filesize":3243733,"bitrate":128,"isnew":0,"duration":202,"album_name":"","singername":"陈奕迅","hash":"180f842eeada43cad376a20db1cb5ec6"},{"filename":"杨洋 - 十年","extname":"mp3","m4afilesize":956999,"filesize":3726941,"bitrate":128,"isnew":0,"duration":233,"album_name":"超级女声1-杨洋之歌","singername":"杨洋","hash":"fa0e6d230e083479afe400ea77db315c"},{"filename":"陈奕迅 - 十年 - DJ版","extname":"mp3","m4afilesize":1522507,"filesize":5828959,"bitrate":128,"isnew":0,"duration":364,"album_name":"","singername":"陈奕迅","hash":"fda8bbb644041f3263d7e63d378265a1"},{"filename":"许魏洲 - 十年","extname":"mp3","m4afilesize":458515,"filesize":1775592,"bitrate":128,"isnew":0,"duration":111,"album_name":"","singername":"许魏洲","hash":"b9857e3f82a15ed28b8ee634a3b8e05c"},{"filename":"霍建华 - 十年","extname":"mp3","m4afilesize":266716,"filesize":1020882,"bitrate":128,"isnew":0,"duration":63,"album_name":"","singername":"霍建华","hash":"6ae220a993e30cb2d3b99e3cf32b2c2a"},{"filename":"搞笑歌曲 - 十年 - 单曲版","extname":"mp3","m4afilesize":862842,"filesize":3281780,"bitrate":128,"isnew":0,"duration":205,"album_name":"","singername":"搞笑歌曲","hash":"f810a932552d32e4c5d776b5b4d6f625"}]}
     */

    private int code;
    private String status;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * current_page : 1
         * keyword : 十年
         * total_rows : 480
         * total_page : 32
         * page_size : 15
         * data : [{"filename":"陈奕迅 - 十年","extname":"mp3","m4afilesize":851201,"filesize":3242822,"bitrate":128,"isnew":0,"duration":202,"album_name":"黑白灰","singername":"陈奕迅","hash":"936051ea140e3cfacb629d2bfaf430d7"},{"filename":"赵丽颖 - 十年【《我们的十年》 电影主题曲】","extname":"mp3","m4afilesize":832156,"filesize":3328348,"bitrate":132,"isnew":0,"duration":201,"album_name":"我们的十年 电影原声带","singername":"赵丽颖","hash":"bc1bf186645db2dc11cb77b53e3d50fa"},{"filename":"韩红、陈奕迅 - 十年","extname":"mp3","m4afilesize":1259429,"filesize":4911226,"bitrate":128,"isnew":0,"duration":307,"album_name":"我是歌手第三季合辑","singername":"韩红、陈奕迅","hash":"7c00d6e8a457ce6ee8a558d37fe37aa3"},{"filename":"TFBOYS - 十年","extname":"mp3","m4afilesize":938719,"filesize":3670644,"bitrate":128,"isnew":0,"duration":229,"album_name":"","singername":"TFBOYS","hash":"b30f03df9b3247118eeef5796e25551a"},{"filename":"南妮 - 十年","extname":"mp3","m4afilesize":816102,"filesize":3175768,"bitrate":129,"isnew":0,"duration":198,"album_name":"唱醉你","singername":"南妮","hash":"5ad184bcdfc3894c1fba8ecd0e689e74"},{"filename":"陈奕迅 - 十年 - 伴奏版","extname":"mp3","m4afilesize":864278,"filesize":3296990,"bitrate":128,"isnew":0,"duration":206,"album_name":"","singername":"陈奕迅","hash":"07c1bb47ef1a3ce3c24196a2aafaa6a2"},{"filename":"陈翔 - 十年","extname":"mp3","m4afilesize":835583,"filesize":3251866,"bitrate":128,"isnew":0,"duration":203,"album_name":"","singername":"陈翔","hash":"2397a8932678d932242de9e8853c2d20"},{"filename":"刘德华 - 十年 - 香港Unforgettable Concert","extname":"mp3","m4afilesize":1175158,"filesize":4511694,"bitrate":128,"isnew":0,"duration":279,"album_name":"","singername":"刘德华","hash":"2e0714a12dc974035c9fca28d57352b8"},{"filename":"王啸坤 - 十年【《最佳前男友》 电视剧插曲】","extname":"mp3","m4afilesize":828362,"filesize":3283967,"bitrate":130,"isnew":0,"duration":201,"album_name":"最佳前男友 电视剧原声带","singername":"王啸坤","hash":"76e3662c0ff2692817985e0e263292b7"},{"filename":"刘若英 - 十年 - 铃声版","extname":"mp3","m4afilesize":150171,"filesize":573149,"bitrate":128,"isnew":0,"duration":36,"album_name":"","singername":"刘若英","hash":"b7a980ebe4a5c2737c5845da35dc5f51"},{"filename":"陈奕迅 - 十年 - 原版伴奏","extname":"mp3","m4afilesize":852411,"filesize":3243733,"bitrate":128,"isnew":0,"duration":202,"album_name":"","singername":"陈奕迅","hash":"180f842eeada43cad376a20db1cb5ec6"},{"filename":"杨洋 - 十年","extname":"mp3","m4afilesize":956999,"filesize":3726941,"bitrate":128,"isnew":0,"duration":233,"album_name":"超级女声1-杨洋之歌","singername":"杨洋","hash":"fa0e6d230e083479afe400ea77db315c"},{"filename":"陈奕迅 - 十年 - DJ版","extname":"mp3","m4afilesize":1522507,"filesize":5828959,"bitrate":128,"isnew":0,"duration":364,"album_name":"","singername":"陈奕迅","hash":"fda8bbb644041f3263d7e63d378265a1"},{"filename":"许魏洲 - 十年","extname":"mp3","m4afilesize":458515,"filesize":1775592,"bitrate":128,"isnew":0,"duration":111,"album_name":"","singername":"许魏洲","hash":"b9857e3f82a15ed28b8ee634a3b8e05c"},{"filename":"霍建华 - 十年","extname":"mp3","m4afilesize":266716,"filesize":1020882,"bitrate":128,"isnew":0,"duration":63,"album_name":"","singername":"霍建华","hash":"6ae220a993e30cb2d3b99e3cf32b2c2a"},{"filename":"搞笑歌曲 - 十年 - 单曲版","extname":"mp3","m4afilesize":862842,"filesize":3281780,"bitrate":128,"isnew":0,"duration":205,"album_name":"","singername":"搞笑歌曲","hash":"f810a932552d32e4c5d776b5b4d6f625"}]
         */

        private int current_page;
        private String keyword;
        private int total_rows;
        private int total_page;
        private int page_size;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getTotal_rows() {
            return total_rows;
        }

        public void setTotal_rows(int total_rows) {
            this.total_rows = total_rows;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * filename : 陈奕迅 - 十年
             * extname : mp3
             * m4afilesize : 851201
             * filesize : 3242822
             * bitrate : 128
             * isnew : 0
             * duration : 202
             * album_name : 黑白灰
             * singername : 陈奕迅
             * hash : 936051ea140e3cfacb629d2bfaf430d7
             */

            private String filename;
            private String extname;
            private int m4afilesize;
            private int filesize;
            private int bitrate;
            private int isnew;
            private int duration;
            private String album_name;
            private String singername;
            private String hash;

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getExtname() {
                return extname;
            }

            public void setExtname(String extname) {
                this.extname = extname;
            }

            public int getM4afilesize() {
                return m4afilesize;
            }

            public void setM4afilesize(int m4afilesize) {
                this.m4afilesize = m4afilesize;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public int getBitrate() {
                return bitrate;
            }

            public void setBitrate(int bitrate) {
                this.bitrate = bitrate;
            }

            public int getIsnew() {
                return isnew;
            }

            public void setIsnew(int isnew) {
                this.isnew = isnew;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getAlbum_name() {
                return album_name;
            }

            public void setAlbum_name(String album_name) {
                this.album_name = album_name;
            }

            public String getSingername() {
                return singername;
            }

            public void setSingername(String singername) {
                this.singername = singername;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            @Override
            public String toString() {
                return "DataBean{" +
                        "filename='" + filename + '\'' +
                        ", extname='" + extname + '\'' +
                        ", m4afilesize=" + m4afilesize +
                        ", filesize=" + filesize +
                        ", bitrate=" + bitrate +
                        ", isnew=" + isnew +
                        ", duration=" + duration +
                        ", album_name='" + album_name + '\'' +
                        ", singername='" + singername + '\'' +
                        ", hash='" + hash + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "MusicQueryBean{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
