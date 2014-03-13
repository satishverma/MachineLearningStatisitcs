
def count(fName):
	f=open(fName,"r")
	wordDict={}
	for line in f.readlines():
		line= line.replace("\n","")
		lineAsList=line.split("\t")
		#print lineAsList
		if (lineAsList[0] in wordDict):
			tmp = wordDict[lineAsList[0]]
			wordDict[lineAsList[0]] = tmp+ int(lineAsList[1])
		else:
			wordDict[lineAsList[0]]= int(lineAsList[1])

	for key, value in wordDict.iteritems() :
        	s= str(key)+","+str(value)
		print(s)


if __name__=="__main__":
	print "HI"
	count("a")
