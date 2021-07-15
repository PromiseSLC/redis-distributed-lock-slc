
# Jmeter新建测试计划（thread-HTTP-assert-view tree）
# 保存计划
# 执行

rm -rf /Users/promise/Desktop/result-annotation.txt
rm -rf /Users/promise/Desktop/report-annotation/
jmeter -n -t /Users/promise/Desktop/Annotation-redis-distributed-lock-test.jmx -l /Users/promise/Desktop/result-annotation.txt -e -o /Users/promise/Desktop/report-annotation