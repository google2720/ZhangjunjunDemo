class Robot:
    """表示有一个带有名字的机器人"""

    #机器人数量
    population = 0;

    def __init__(self,name):
        """初始化数据"""
        self.name = name
        print("(Initializing {})".format(self.name))

        #当人口被创建时，数量加1
        Robot.population +=1;

    def die(self):
        """我挂了"""
        print("{}  死了！".format(self.name))

        Robot.population -=1

        if Robot.population == 0:
            print("{} 是最后一个人！".format(self.name))
        else:
            print("还有{:d}个机器人在工作".format(Robot.population))

    def say_hi(self):
        print("我是机器人{}".format(self.name))

    @classmethod
    def how_many(self):
        print("当前有{:d}个机器人".format(self.population))



droid1 = Robot("R2-D2")
droid1.say_hi()
Robot.how_many()


droid2 = Robot("C-3p0")
droid2.say_hi()
Robot.how_many()


droid1.die()
droid2.die()

Robot.how_many()
