package event;

import java.util.List;

import model.VedioBean;

/**
 * Created by Administrator on 2016/12/14.
 */

public class VedioEvent {

    private List<VedioBean> list;

    public VedioEvent(List<VedioBean> list) {
        this.list = list;
    }

    public List<VedioBean> getList() {
        return list;
    }
}
