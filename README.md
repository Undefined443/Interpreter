# Interpreter

一款函数绘图语言解释器

源文件：

heart.txt

```sql
-- 箭头
color is (255, 255, 0);

scale is (1, 1);
size is 10;

origin is (450, 450);
rot is pi;
for t from 0 to 400 step 1 draw(t, t);

-- 心形曲线
origin is (200, 200);
rot is pi/2;
size is 5;

-- 圆润型
color is (0, 0, 255);
scale is (50, 50);
for t from -pi to pi step pi/200 draw((2*cos(t)-cos(2*t)), (2*sin(t)-sin(2*t)));

-- 尖锐型
origin is (200+80, 200+80);
color is (255, 0, 0);
rot is pi;
scale is (8, 8);
for t from 0 to 2*pi step pi/200 draw(16*(sin(t)**3), 13*cos(t)-5*cos(2*t)-2*cos(3*t)-cos(4*t));
```

编译运行（要求 Java 版本至少为 11）：

```sh
# 编译
mvn package

# 运行
java -jar target/Interpreter-1.0.0-jar-with-dependencies.jar heart.txt
```

输出：

![pic](.assets/demo1.png)
