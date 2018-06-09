print("Hello Word")#注意到 print 是一个函数



#字面常量   5,1.23 , This is a string , 这是一串文本

#数字 整数（Integers）与浮点数（Floats）

#字符串 一串字符串（String）是 字符（Characters） 的 序列（Sequence）。基本上，字符串就是一串词汇。
   #单引号  '将我这样框进来'  或  'Quote me on this' 引号内的空间，诸如空格与制表符，都将按原样保留
   #双引号   被双引号包括的字符串和被单引号括起的字符串其工作机制完全相同
   #三引号   三个引号—— """  或  '''  来指定多行字符串

a='''这是一段多行字符串。这是它的第一行。
This is the second line.
"What's your name?," I asked.
He said "Bond, James Bond."
'''

print(a)

age = 20
name = 'Swaroop'
print('{0} was {1} years old when he wrote this book'.format(name, age))
print('Why is {0} playing with that python?'.format(name))

print(name + 'is' + str(age)+'year old') #这样写法很丑

#还可以这样写
print('{} was {}   years old when he wrote this book'.format(name,age))


#Python 中  format  方法所做的事情便是将每个参数值替换至格式所在的位置
# 对于浮点数 '0.333' 保留小数点(.)后三位
print('{0:.3f}'.format(1.0/3))
# 使用下划线填充文本，并保持文字处于中间位置
# 使用 (^) 定义 '___hello___'字符串长度为 11
print('{0:_^11}'.format('hello'))
# 基于关键词输出 'Swaroop wrote A Byte of Python'
print('{name} wrote {book}'.format(name='Swaroop', book='A Byte of Python'))


#print  总是会以一个不可见的“新一行”字符（ \n  ）
#结尾，因此重复调用  print  将会在相互独立的一行中分别打印。为防止打印过程中出现这一
#换行符，你可以通过  end  指定其应以空白结尾：
print('a', end='')
print('b', end='')


#转义序列
print('This is the first line\nThis is the second line')
print("What's your name?")
print('What\'s your name?')# '转义
print('\\')# 输出 \


#原始字符串
# 如果你需要指定一些未经过特殊处理的字符串，比如转义序列，那么你需要在字符串前增加
#r  或  R  来指定一个 原始（Raw） 字符串
print(r"Newlines are indicated by \n")



#变量
  #标识符命名
