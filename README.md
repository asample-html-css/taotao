# taotao
传智播客淘淘商城

改编自传智播客大型分布式网络商城 淘淘商城项目  jdk1.8 + idea + git


一、nginx.conf:


 server {
        listen       80;
        server_name  solr.taotao.com;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	proxy_set_header Host $host;

        location / {
	    proxy_pass http://127.0.0.1:8983;
	    proxy_connect_timeout 600;
	    proxy_read_timeout 600;
        }
        
    }


 server {
        listen       80;
        server_name  order.taotao.com;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

        location / {
	    proxy_pass http://127.0.0.1:8084;
	    proxy_connect_timeout 600;
	    proxy_read_timeout 600;
        }
        
    }


server {
        listen       80;
        server_name  sso.taotao.com;
    
        #charset koi8-r;
    
        #access_log  logs/host.access.log  main;
    
    	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	proxy_set_header Host $host;
    
        location / {
    	    proxy_pass http://127.0.0.1:8083;
    	    proxy_connect_timeout 600;
    	    proxy_read_timeout 600;
        }
        
    } 


server {
        listen       80;
        server_name  manage.taotao.com;
    
        #charset koi8-r;
    
        #access_log  logs/host.access.log  main;
    
    	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    
        location / {
    	    proxy_pass http://127.0.0.1:8082;
    	    proxy_connect_timeout 600;
    	    proxy_read_timeout 600;
        }
        
    } 


 server {
        listen       80;
        server_name  www.taotao.com taotao.com;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

        location / {
	    proxy_pass http://127.0.0.1:8081;
	    proxy_connect_timeout 600;
	    proxy_read_timeout 600;
        }
        
    }


    server {
        listen       80;
        server_name  image.taotao.com;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

        location / {
	    root  D:\\taotao-upload;
        }
        
    }


    server {
        listen       80;
        server_name  static.taotao.com;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

	proxy_set_header X-Forwarded-Host $host;
        proxy_set_header X-Forwarded-Server $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

        location / {
	    root  D:\\taotao-static;
        }
        
    }


二.tomcat配置
后台系统 taotao-manage
http://localhost:8082/  
JMX port  1092

前台系统 taotao-web
http://localhost:8081/  
JMX port  1091

单点登录系统 taotao-sso
http://localhost:8083/  
JMX port  1093

订单系统 taotao-order
http://localhost:8084/  
JMX port  1094


三.域名映射  C:\Windows\System32\drivers\etc\hosts

127.0.0.1 manage.taotao.com
127.0.0.1 image.taotao.com
127.0.0.1 order.taotao.com
127.0.0.1 www.taotao.com
127.0.0.1 sso.taotao.com
127.0.0.1 solr.taotao.com
127.0.0.1 static.taotao.com
127.0.0.1 localhost





四、前台首页广告数据结构分析

图一：从数据库获取的原生数据

图二：通过 private static final ObjectMapper MAPPER = new ObjectMapper();   拿到的rows中的数据  图中显示其中一个row的数据
	//将json数据反序列化成jsonNode
JsonNode jsonNode = MAPPER.readTree(jsonData);
//将数据封装成数组  拿到返回数据中的rows
ArrayNode rows = (ArrayNode) jsonNode.get("rows");

图三：遍历rows，封装成 List<Map<String,Object>>结构   然后，在反序列化成	String
MAPPER.writeValueAsString(result);//将json对象序列化成json字符串


图片链接地址：
http://pan.baidu.com/s/1nvhtPbF
















