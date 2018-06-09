import os
import time

source = 'F:\\testZip'
target_dir = 'F\\testZipBak'

#如果没有目录，创建目录
if not os.path.exists(target_dir):
    os.mkdir(target_dir)

today =target_dir + os.sep + time.strftime('%Y%m%d')
now = time.strftime('%H%M%S')


#添加一条来自用户的注释以创建


