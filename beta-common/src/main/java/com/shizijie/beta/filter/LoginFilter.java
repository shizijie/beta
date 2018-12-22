package com.shizijie.beta.filter;

import com.shizijie.beta.bean.user.AuthContext;
import com.shizijie.beta.bean.user.dto.UserDTO;
import com.shizijie.beta.model.ResultBean;
import com.shizijie.beta.params.BetaParams;
import com.shizijie.beta.redis.RedisService;
import lombok.Cleanup;
import lombok.SneakyThrows;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.Beta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author shizijie
 * @version 2018-12-15 下午9:16
 */
public class LoginFilter implements Filter {

    private boolean check=false;

    private RedisService redisService;

    private String rootPath;

    private List<String> passUrl;

    private int expireTime;

    public LoginFilter(RedisService redisService,Environment env){
        this.redisService=redisService;
        this.rootPath=env.getProperty(BetaParams.ROOT_PATH);
        if("true".equals(env.getProperty(BetaParams.FILTER_ON_OFF))){
            this.check = true;
        }
        if(StringUtils.isNotBlank(env.getProperty(BetaParams.FILTER_PASS_URL))){
            this.passUrl= Arrays.asList(env.getProperty(BetaParams.FILTER_PASS_URL).split(","));
        }
        this.expireTime=Integer.parseInt(env.getProperty(BetaParams.SESSION_TIME_OUT));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        UserDTO userDTO=null;
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse resp= (HttpServletResponse) response;
        if(check){
            String token=req.getHeader(BetaParams.TOKEN);
            if(StringUtils.isBlank(token)){
                returnError(resp);
                return;
            }
            String url;
            if(StringUtils.isNotBlank(rootPath)&&req.getRequestURI().indexOf(rootPath)!=-1){
                url=req.getRequestURI().substring(req.getRequestURI().indexOf(rootPath)+rootPath.length());
            }
            url=req.getRequestURI();
            if(passUrl==null||!passUrl.contains(url)){
                userDTO=checkUrl(token,url);
                if(userDTO==null){
                    returnError(resp);
                    return;
                }
            }
            redisService.setExpire(token,expireTime);
        }else{
            userDTO=new UserDTO();
        }
        AuthContext.setUserInfo(userDTO);
        chain.doFilter(req,resp);
        AuthContext.clear();
    }

    @Override
    public void destroy() {

    }
    @SneakyThrows
    public void returnError(HttpServletResponse resp){
        ResultBean resultBean=ResultBean.fail403();
        resp.setStatus(401);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");
        @Cleanup PrintWriter out=resp.getWriter();
        out.append(JSONObject.fromObject(resultBean).toString());
    }

    public UserDTO checkUrl(String token,String url){
        UserDTO user= (UserDTO) redisService.get(token);
        if(user==null){
            return null;
        }
        if(user.getAuthList()==null||!user.getAuthList().contains(url)){
            return null;
        }
        return user;
    }
}
