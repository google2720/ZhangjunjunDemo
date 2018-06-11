try:
    text = input('Enter something -->')
except EOFError:
    print("Why did you do an EOF on me?")
except KeyboardInterrupt:
    print("you cancelled the operation ")
else:
    print("You extered {}".format(text))
