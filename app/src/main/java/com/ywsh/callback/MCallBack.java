package com.ywsh.callback;

import com.ywsh.bean.Account;

/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 * Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/5/18
 * Descroption:
 */
public interface MCallBack {
    public void onSuccess(Account account);
    public  void onFailed();
}

