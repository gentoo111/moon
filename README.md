# moon
Spring Boot Project
该项目重构于2018年3月
目前维护人员:
    szz,sofeya2014,MkStone
<1>首次部署当前程序需要在对应的文件夹中执行以下命令
a.启动程序 nohup java -jar moon.jar & 
b.退出 ctrl + c 
c.查看日志 tail -500f nohup.out
<2>非首次部署当前程序需要在对应的文件夹中执行以下命令
a.捕获上一个版本程序的进程 ps - ef|grep moon.jar 
b.杀死对应的进程 kill 进程号 
c.启动程序 nohup java -jar moon.jar & 
d.退出 ctrl + c 
e.查看日志 tail -500f nohup.out
