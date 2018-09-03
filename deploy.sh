echo "===========进入git项目mmall目录============="
cd /developer/git-repository/mmall


echo "==========切换到v1.0(发布分支）==============="
git checkout v1.0

echo "==================git fetch======================"
git fetch

echo "==================git pull======================"
git pull


echo "===========编译并跳过单元测试===================="
mvn clean package -Dmaven.test.skip=true


echo "============删除旧的ROOT.war==================="
rm /developer/tomcat-7.0.73/webapps/ROOT.war


echo "======拷贝编译出来的war包到tomcat下-ROOT.war======="
cp /developer/git-repository/mmall/target/mmall.war  /developer/tomcat-7.0.73/webapps/ROOT.war


echo "============删除tomcat下旧的ROOT文件夹============="
rm -rf /developer/tomcat-7.0.73/webapps/ROOT



echo "====================关闭tomcat====================="
/developer/tomcat-7.0.73/bin/shutdown.sh


echo "================sleep 10s========================="
for i in {1..10}
do
	echo $i"s"
	sleep 1s
done


echo "====================启动tomcat====================="
/developer/tomcat-7.0.73/bin/startup.sh




