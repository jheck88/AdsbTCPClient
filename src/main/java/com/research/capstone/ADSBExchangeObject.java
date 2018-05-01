package com.research.capstone;

import java.util.List;

public class ADSBExchangeObject {

    int src;
    String[] feeds;
    int srcFeed;
    boolean showSil;
    boolean showFlg;
    boolean showPic;
    int flgH;
    int flgW;
    String[] acList;

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public String[] getFeeds() {
        return feeds;
    }

    public void setFeeds(String[] feeds) {
        this.feeds = feeds;
    }

    public int getSrcFeed() {
        return srcFeed;
    }

    public void setSrcFeed(int srcFeed) {
        this.srcFeed = srcFeed;
    }

    public boolean isShowSil() {
        return showSil;
    }

    public void setShowSil(boolean showSil) {
        this.showSil = showSil;
    }

    public boolean isShowFlg() {
        return showFlg;
    }

    public void setShowFlg(boolean showFlg) {
        this.showFlg = showFlg;
    }

    public boolean isShowPic() {
        return showPic;
    }

    public void setShowPic(boolean showPic) {
        this.showPic = showPic;
    }

    public int getFlgH() {
        return flgH;
    }

    public void setFlgH(int flgH) {
        this.flgH = flgH;
    }

    public int getFlgW() {
        return flgW;
    }

    public void setFlgW(int flgW) {
        this.flgW = flgW;
    }

    public String[] getAcList() {
        return acList;
    }

    public void setAcList(String[] acList) {
        this.acList = acList;
    }

}
