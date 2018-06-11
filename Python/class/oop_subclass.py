class SchoolMember:
    """代表任何学校的成员"""
    def __init__(self,name,age):
        self.name = name
        self.age = age
        print('初始化学校成员:{}'.format(self.name))

    def tell(self):
        print("Name :{} Age:{}".format(self.name,self.age), end=" ")

class Teacher(SchoolMember):
    def __init__(self,name,age,salary):
        SchoolMember.__init__(self,name,age)
        self.salary = salary
        print("初始化老师{}".format(self.name))

    def tell(self):
        SchoolMember.tell(self)
        print("Salary: {:d}".format(self.salary))

class Student(SchoolMember):
    def __init__(self,name,age,marks):
        SchoolMember.__init__(self,name,age)
        self.marks = marks
        print("初始化学生:{}".format(self.name))

    def tell(self):
        SchoolMember.tell(self)
        print("Marks: {:d}".format(self.marks))


t = Teacher("zhangjunjun",40,30000)
s = Student("xingjinjin",25,76)


print()

members = [t,s]

for member in members:
    member.tell()






