package com.ywsh.model;

import com.ywsh.bean.Account;
import com.ywsh.callback.MCallBack;

import java.util.Random;

/**
 * Copyright, 2020, WhyHow info, All right reserved.
 *
 * @author Administrator
 * Mail: liyongchao@whyhowinfo.com
 * Created Time: 2020/5/18
 * Descroption:
 */
public class MVVMModel {

    //模拟查询账号数据
    public void getAccountData(String accountName, MCallBack callback){
        Random random = new Random();
        boolean isSuccess = random.nextBoolean();
        if(isSuccess){
            Account account = new Account();
            account.setName(accountName);
            account.setLevel(100);
            callback.onSuccess(account);
        }else {
            callback.onFailed();
        }
    }
}