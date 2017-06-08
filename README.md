# taotao
传智播客淘淘商城

改编自传智播客大型分布式网络商城 淘淘商城项目  jdk1.8 + idea + git

idea相关配置

1、nginx:

后台系统
server {
        listen       80;
        server_name  manage.taotao.com;
        location / {
    	    proxy_pass http://127.0.0.1:8082;
        }
    } 

前台系统
 server {
        listen       80;
        server_name  www.taotao.com taotao.com;
        location / {
	    proxy_pass http://127.0.0.1:8081;
        }
    }

//图片服务器
    server {
        listen       80;
        server_name  image.taotao.com;
        location / {
	    root  D:\\taotao-upload;
        }
    }
}

2.tomcat配置
后台系统 taotao-manage
http://localhost:8082/  
JMX port  1092

前台系统 taotao-web
http://localhost:8081/  
JMX port  1091










