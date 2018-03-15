package com.tf.base.common.interceptor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tf.base.common.constants.ConstantsUtils;
import com.tf.base.common.service.BaseService;
import com.tf.base.user.domain.UserInfo;


public class ControllerInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);

	@Autowired
	private BaseService baseService;
	
	/**
     * handler执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws HttpSessionRequiredException {
    	if(request.getServletPath().indexOf("emailSendController/send")!=-1){
    		//发送邮件接口
    		System.out.println("==================屏蔽掉拦截器====================");
    		return true;
    	}
        // SESSION为空时，抛出SESSION异常
        if (request.getSession() == null) {

            throw new HttpSessionRequiredException("noSession");
        }
        
        String ip = getIp(request);
        String mac;
        
        try{
        	mac = getMac(ip);
        } catch (IOException e) {
        	
        	mac = "";
        }
        
        UserInfo user = (UserInfo) request.getSession().getAttribute(ConstantsUtils.CURRENT_USER);
        
        String name = null;
        
        if (user != null) {
        	name = user.getUsername();
        }
        
        baseService.setBaseInfo(ip, mac, user);
       
        if (log.isInfoEnabled()) {

            StringBuffer sb = new StringBuffer();
            sb.append("***");
            sb.append(" Controller preHandle");
            sb.append(" url:");
            sb.append(request.getRequestURI());
            sb.append(" user:");
            sb.append(name);
            sb.append(" ***");

            log.info(sb.toString());
        }

        return true;
    }
    
    /**
     * handler执行后
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView){

        if (log.isInfoEnabled()) {

            StringBuffer sb = new StringBuffer();
            sb.append("***");
            sb.append(" Controller postHandle");
            sb.append(" url:");
            sb.append(modelAndView == null ? null : modelAndView.getViewName());
            sb.append(" ***");

            log.info(sb.toString());
        }
    }
    
    private String getIp(HttpServletRequest request) {
    	
    	String sip="";
    	
    	sip = request.getHeader("x-forwarded-for");
        if(sip == null ||sip.length() == 0 || "unknown".equalsIgnoreCase(sip))
        {
        sip = request.getHeader("proxy-Client-IP");
        }
        if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) 
        {
            sip = request.getHeader("WL-Proxy-Client-IP");
        } 
        if (sip == null ||sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) 
        {
            sip = request.getRemoteAddr();
        }
        
        return sip;
    }
    
    private String getMac(String sip) throws IOException{
    	
    	String smac="";
    	
    	byte[] buffer = new byte[1024];
        
        byte[] t_ns = new byte[50];
        t_ns[0] = 0x00;
           t_ns[1] = 0x00;
           t_ns[2] = 0x00;
           t_ns[3] = 0x10;
           t_ns[4] = 0x00;
           t_ns[5] = 0x01;
           t_ns[6] = 0x00;
           t_ns[7] = 0x00;
           t_ns[8] = 0x00;
           t_ns[9] = 0x00;
           t_ns[10] = 0x00;
           t_ns[11] = 0x00;
           t_ns[12] = 0x20;
           t_ns[13] = 0x43;
           t_ns[14] = 0x4B;
           
           for(int i = 15; i < 45; i++)
           {
            t_ns[i] = 0x41;
           }
           
           t_ns[45] = 0x00;
           t_ns[46] = 0x00;
           t_ns[47] = 0x21;
           t_ns[48] = 0x00;
           t_ns[49] = 0x01;
           
        DatagramSocket ds = new DatagramSocket();
        
        ds.setSoTimeout(500);
        
        DatagramPacket dp = new DatagramPacket(t_ns,t_ns.length,InetAddress.getByName(sip),137);
        
        ds.send(dp);
        
        DatagramPacket dpr = new DatagramPacket(buffer,buffer.length);
        ds.receive(dpr);
        
        byte[] brevdata = dpr.getData();
        
        int i = brevdata[56] * 18 + 56;
        String sAddr="";
        StringBuffer sb = new StringBuffer(17);
        
        for(int j = 1; j < 7;j++)
        {
         sAddr = Integer.toHexString(0xFF & brevdata[i+j]);
            if(sAddr.length() < 2)
            {
             sb.append(0);
            }
            sb.append(sAddr.toUpperCase());
            if(j < 6) sb.append(':');
        }
        
        smac = sb.toString();
        
        ds.close();
        
        return smac;
    }
}
