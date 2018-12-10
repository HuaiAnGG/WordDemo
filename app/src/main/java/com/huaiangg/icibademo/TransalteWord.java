package com.huaiangg.icibademo;

import java.util.List;

/**
 * @description:
 * @author: HuaiAngg
 * @create: 2018-12-08 20:53
 */
public class TransalteWord {

    /**
     * status : 0
     * content : {"ph_en":"wɜ:d","ph_am":"wɜrd","ph_en_mp3":"http://res.iciba.com/resource/amp3/0/0/c4/7d/c47d187067c6cf953245f128b5fde62a.mp3","ph_am_mp3":"http://res.iciba.com/resource/amp3/1/0/c4/7d/c47d187067c6cf953245f128b5fde62a.mp3","ph_tts_mp3":"http://res-tts.iciba.com/c/4/7/c47d187067c6cf953245f128b5fde62a.mp3","word_mean":["n. 单词;话语;诺言;消息;","vt. 措辞，用词;用言语表达;","vi. 讲话;"]}
     */

    private int status;
    private Content content;

    @Override
    public String toString() {
        return "TransalteWord{" +
                "status=" + status +
                ", " + content.toString() +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content {
        /**
         * ph_en : wɜ:d
         * ph_am : wɜrd
         * ph_en_mp3 : http://res.iciba.com/resource/amp3/0/0/c4/7d/c47d187067c6cf953245f128b5fde62a.mp3
         * ph_am_mp3 : http://res.iciba.com/resource/amp3/1/0/c4/7d/c47d187067c6cf953245f128b5fde62a.mp3
         * ph_tts_mp3 : http://res-tts.iciba.com/c/4/7/c47d187067c6cf953245f128b5fde62a.mp3
         * word_mean : ["n. 单词;话语;诺言;消息;","vt. 措辞，用词;用言语表达;","vi. 讲话;"]
         */

        private String ph_en;
        private String ph_am;
        private String ph_en_mp3;
        private String ph_am_mp3;
        private String ph_tts_mp3;
        private List<String> word_mean;

        public String getPh_en() {
            return ph_en;
        }

        public void setPh_en(String ph_en) {
            this.ph_en = ph_en;
        }

        public String getPh_am() {
            return ph_am;
        }

        public void setPh_am(String ph_am) {
            this.ph_am = ph_am;
        }

        public String getPh_en_mp3() {
            return ph_en_mp3;
        }

        public void setPh_en_mp3(String ph_en_mp3) {
            this.ph_en_mp3 = ph_en_mp3;
        }

        public String getPh_am_mp3() {
            return ph_am_mp3;
        }

        public void setPh_am_mp3(String ph_am_mp3) {
            this.ph_am_mp3 = ph_am_mp3;
        }

        public String getPh_tts_mp3() {
            return ph_tts_mp3;
        }

        public void setPh_tts_mp3(String ph_tts_mp3) {
            this.ph_tts_mp3 = ph_tts_mp3;
        }

        public List<String> getWord_mean() {
            return word_mean;
        }

        public void setWord_mean(List<String> word_mean) {
            this.word_mean = word_mean;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "ph_en='" + ph_en + '\'' +
                    ", ph_am='" + ph_am + '\'' +
                    ", ph_en_mp3='" + ph_en_mp3 + '\'' +
                    ", ph_am_mp3='" + ph_am_mp3 + '\'' +
                    ", ph_tts_mp3='" + ph_tts_mp3 + '\'' +
                    ", word_mean=" + word_mean +
                    '}';
        }

    }
}
