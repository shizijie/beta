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
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author shizijie
 * @version 2018-12-15 下午9:16
 */
public class LoginFilter implements Filter {

    private boolean check=false;

    private RedisService redisService;

    private WebApplicationContext applicationContext;

    private String rootPath;

    private List<String> passUrl;

    private int expireTime;

    public LoginFilter(RedisService redisService,Environment env,WebApplicationContext applicationContext){
        this.redisService=redisService;
        this.rootPath=env.getProperty(BetaParams.ROOT_PATH);
        this.applicationContext=applicationContext;
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
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            // 获取url与类和方法的对应信息
            Map<RequestMappingInfo, org.springframework.web.method.HandlerMethod> map = mapping.getHandlerMethods();
            List<String> urlList = new ArrayList<>();
//            for (RequestMappingInfo info : map.keySet()) {
//                // 获取url的Set集合，一个方法可能对应多个url
//                Set<String> patterns = info.getPatternsCondition().getPatterns();
//                urlList.addAll(new ArrayList<>(patterns));
//            }
//            System.out.println(urlList);
//            AntPathMatcher matcher = new AntPathMatcher();
//            Map<String, String> result=new HashMap<>();
//            for(String str:urlList){
//                if(matcher.match(str, req.getRequestURI())){
//                    result.putAll(matcher.extractUriTemplateVariables(str, req.getRequestURI()));
//                }
//            }
//            System.out.println(result);
//            userDTO=new UserDTO();
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
