package com.github.sd4324530.fastweixin;

import com.github.sd4324530.fastweixin.api.*;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.CustomAccount;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MediaType;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.api.response.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peiyu
 */
public class FastweixinTest {

    private static final Logger LOG = LoggerFactory.getLogger(FastweixinTest.class);

    @Test
    public void test() {
        String appid = "wxafb7b8f9457b5d50";
        String secret = "1b8223018a69658f0236d68d2e41fb20";
        ApiConfig config = new ApiConfig(appid, secret);
//        createMenu(config);
//        getUserList(config);
//        uploadMedia(config);
//        downloadMedia(config);
//        getUserInfo(config);
//        getMenu(config);
//        addCustomAccount(config);
//        getOauthPageUrl(config);
//        getToken(config);
        oauthGetUserInfo(config);
    }

    /**
     * 创建菜单
     *
     * @param config API配置
     */
    private void createMenu(ApiConfig config) {
        MenuAPI menuAPI = new MenuAPI(config);

        //先删除之前的菜单
        menuAPI.deleteMenu();
        Menu request = new Menu();
        //准备一级主菜单
        MenuButton main1 = new MenuButton();
        main1.setType(MenuType.CLICK);
        main1.setKey("main1");
        main1.setName("测试");
        //准备子菜单
        MenuButton sub1 = new MenuButton();
        sub1.setKey("sub1");
        sub1.setName("授权");
        sub1.setType(MenuType.VIEW);
        sub1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxafb7b8f9457b5d50&redirect_uri=http://121.40.140.41/erhuluanzi/app/testGet&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");

        List<MenuButton> list = new ArrayList<MenuButton>();
        list.add(sub1);
        //将子菜单放入主菜单里
        main1.setSubButton(list);

        List<MenuButton> mainList = new ArrayList<MenuButton>();
        mainList.add(main1);
        //将主菜单加入请求对象
        request.setButton(mainList);
        LOG.debug(request.toJsonString());
        //创建菜单
        ResultType resultType = menuAPI.createMenu(request);
        LOG.debug(resultType.toString());
    }

    /**
     * 获取关注者列表
     *
     * @param config API配置
     */
    public void getUserList(ApiConfig config) {
        UserAPI userAPI = new UserAPI(config);
        GetUsersResponse users = userAPI.getUsers(null);
        LOG.debug("user count:{}", users.getCount());
        LOG.debug("user total:{}", users.getTotal());
        String[] openids = users.getData().getOpenid();
        for (String id : openids) {
            LOG.debug("id:{}", id);
        }
    }

    /**
     * 获取用户信息
     *
     * @param config API配置
     */
    public void getUserInfo(ApiConfig config) {
        UserAPI userAPI = new UserAPI(config);
        GetUserInfoResponse userInfo = userAPI.getUserInfo("opZYwt-OS8WFxwU-colRzpu50eOQ");
        LOG.debug(userInfo.toJsonString());
    }

    public void uploadMedia(ApiConfig config) {
        MediaAPI mediaAPI = new MediaAPI(config);
        UploadMediaResponse response = mediaAPI.uploadMedia(MediaType.IMAGE, new File("E:/123.jpg"));
        LOG.debug(response.toJsonString());
    }

    public void downloadMedia(ApiConfig config) {
        MediaAPI mediaAPI = new MediaAPI(config);
        DownloadMediaResponse response = mediaAPI.downloadMedia("Kw0k6yeKxLaebweRwAUS2x08bcOx2nHMWAXO4s1lMpN_t5Fcsm-svrxe_EfGAgwo");
        LOG.debug("error:{}", response.getErrcode());
        try {
            response.writeTo(new FileOutputStream(new File("E:/222.jpg")));
        } catch (FileNotFoundException e) {
            LOG.error("异常", e);
        } catch (IOException e) {
            LOG.error("异常", e);
        }
    }

    public void getMenu(ApiConfig config) {
        MenuAPI api = new MenuAPI(config);
        GetMenuResponse response = api.getMenu();
        LOG.debug("菜单:{}", response.toJsonString());
    }

    public void addCustomAccount(ApiConfig config) {
        CustomAPI customAPI = new CustomAPI(config);
        CustomAccount customAccount = new CustomAccount();
        customAccount.setAccountName("peiyu@i-xiaoshuo");
        customAccount.setNickName("帅哥");
//        customAccount.setPassword("123456");
        ResultType resultType = customAPI.addCustomAccount(customAccount);
        LOG.debug("添加结果:{}", resultType.toString());
    }

    public void getOauthPageUrl(ApiConfig config) {
        OauthAPI oauthAPI = new OauthAPI(config);
        String pageUrl = oauthAPI.getOauthPageUrl("http://121.40.140.41/erhuluanzi/app/testGet", OauthScope.SNSAPI_BASE, "123");
        LOG.debug("pageUrl:{}", pageUrl);
    }

    public void getToken(ApiConfig config) {
        OauthAPI oauthAPI = new OauthAPI(config);
        OauthGetTokenResponse response = oauthAPI.getToken("041821d373d6a18679cb0b1d8d5cc1ez");
        LOG.debug("response:{}", response.toJsonString());
    }

    public void oauthGetUserInfo(ApiConfig config) {
        OauthAPI oauthAPI = new OauthAPI(config);
        GetUserInfoResponse response = oauthAPI.getUserInfo("OezXcEiiBSKSxW0eoylIeKoEzhGrPf8vRE3NugAdMy16Em-NimErLsOMfMlZBW0P0wauuYLIzl1soHnV-9CGvQtUYxmd3F6ruwjs_SQNw90aZd_yFlVc85P2FlC01QVNyRktVrSX5zHIMkETyjZojQ", "opZYwt-OS8WFxwU-colRzpu50eOQ");
        LOG.debug("response:{}", response.toJsonString());

    }
}
