# taotao
传智播客淘淘商城

改编自传智播客大型分布式网络商城 淘淘商城项目  jdk1.8 + idea + git

一、idea相关配置

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


二、前台首页广告数据结构分析

图一：从数据库获取的原生数据

图二：通过 private static final ObjectMapper MAPPER = new ObjectMapper();   拿到的rows中的数据  图中显示其中一个row的数据
	//将json数据反序列化成jsonNode
JsonNode jsonNode = MAPPER.readTree(jsonData);
//将数据封装成数组  拿到返回数据中的rows
ArrayNode rows = (ArrayNode) jsonNode.get("rows");

图三：遍历rows，封装成 List<Map<String,Object>>结构   然后，在反序列化成	String
MAPPER.writeValueAsString(result);//将json对象序列化成json字符串
















