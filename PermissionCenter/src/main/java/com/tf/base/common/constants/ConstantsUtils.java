package com.tf.base.common.constants;


public class ConstantsUtils {

	public static String MENU = "menu"; //资源类型
	public static final String CURRENT_USER = "user";
	
public enum LOG_OPERATION_TYPE {
		
		CREATE("0"),
		VIEW("1"),
		MODIFY("2"),
		DELETE("3");
		
		private String value;  
		
		LOG_OPERATION_TYPE(String value) {  
            this.value = value;  
        }  
          
        public String toString() {  
            return value;  
        }  
	}
	
	public  enum LOG_MODULE {
		
		USERINFO("0"),
		ROLEINFO("1"),
		DEPARTMENT("3"),
		SYSTEMINFO("4"),
		RESOURCESINFO("5");
		private String value;  
		
		LOG_MODULE(String value) {  
            this.value = value;  
        }  
          
        public String toString() {  
            return value;  
        }  
	}

}
