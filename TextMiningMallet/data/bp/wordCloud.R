require(wordcloud)
require(RColorBrewer)

input<-"rinput.txt"
data<-read.csv(input,h=FALSE)
head(data)

pal2 <- brewer.pal(8,"Dark2")
png("lda.png", width=1280,height=800)

minFreq<-1
maxFreq<-Inf

wordcloud(as.vector(data$V1),as.vector(data$V2),c(5,1),minFreq,maxFreq,random.order=FALSE,random.color=TRUE,rot.per=0.0,colors=pal2)
dev.off()




