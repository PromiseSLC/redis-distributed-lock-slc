# Jmeter新建测试计划（thread-HTTP-assert-view tree）
# 保存计划
# 执行

rm -rf /Users/promise/Desktop/result.txt
rm -rf /Users/promise/Desktop/report/
jmeter -n -t /Users/promise/Desktop/TestPlan.jmx -l /Users/promise/Desktop/result.txt -e -o /Users/promise/Desktop/report
