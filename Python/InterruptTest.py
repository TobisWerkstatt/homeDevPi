import RPi.GPIO as GPIO
import time
import smbus
import os.path

PCF8574=0x38
bus = smbus.SMBus(1)
bus.write_byte(PCF8574, 0xff)
Counter1 = 0
Counter2 = 0
Counter3 = 0
Counter4 = 0
Counter5 = 0
Counter6 = 0
Counter7 = 0
Counter8 = 0

GPIO.setmode(GPIO.BCM) 
GPIO.setup(5, GPIO.IN, pull_up_down = GPIO.PUD_UP)

def int_Counter(channel):
	global Counter1
	global Counter2
	global Counter3
	global Counter4
	global Counter5
	global Counter6
	global Counter7
	global Counter8
	
	pins = bus.read_byte(PCF8574)
	
	if (pins & 0x01)==0x00:
		Counter1 = Counter1 + 1
		print "Counter1 " + str(Counter1)
	
	if (pins & 0x02)==0x00:
		Counter2 = Counter2 + 1
		print "Counter2 " + str(Counter2)
	
	if (pins & 0x04)==0x00:
		Counter3 = Counter3 + 1
		print "Counter3 " + str(Counter3)
	
	if (pins & 0x08)==0x00:
		Counter4 = Counter4 + 1
		print "Counter4 " + str(Counter4)
	
	if (pins & 0x10)==0x00:
		Counter5 = Counter5 + 1
		print "Counter5 " + str(Counter5)
	
	if (pins & 0x20)==0x00:
		Counter6 = Counter6 + 1
		print "Counter6 " + str(Counter6)
	
	if (pins & 0x40)==0x00:
		Counter7 = Counter7 + 1
		print "Counter7 " + str(Counter7)
	
	if (pins & 0x80)==0x00:
		Counter8 = Counter8 + 1
		print "Counter8 " + str(Counter8)	
		
	print "Pin " + hex(pins)
	
def logData(channel):
	pins = bus.read_byte(PCF8574)
	
	if (pins & 0x01)==0x00:
		writeToFile("Counts_S0A.txt")
		
	if (pins & 0x02)==0x00:
		writeToFile("Counts_S0B.txt")
		
	if (pins & 0x04)==0x00:
		writeToFile("Counts_S0C.txt")
		
	if (pins & 0x08)==0x00:
		writeToFile("Counts_S0D.txt")
		
	if (pins & 0x10)==0x00:
		writeToFile("Counts_S0E.txt")
		
	if (pins & 0x20)==0x00:
		writeToFile("Counts_S0F.txt")
		
	if (pins & 0x40)==0x00:
		writeToFile("Counts_S0G.txt")
		
	if (pins & 0x80)==0x00:
		writeToFile("Counts_S0H.txt")

def writeToFile(filename):
	if(os.path.isfile(filename)):
		f = open(filename, 'a')
	else:
		f = open(filename, 'w')
	f.write(time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime()) + "\n")
	print("x")
	f.close()
		
#GPIO.add_event_detect(5, GPIO.FALLING, callback = int_Counter)
GPIO.add_event_detect(5, GPIO.FALLING, callback = logData)

# Endlosschleife
while True:
	time.sleep(1)