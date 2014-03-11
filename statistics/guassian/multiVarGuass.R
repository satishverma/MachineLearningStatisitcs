
#Code to see some properties of multivariate guassian
#Tutorial URL http://www.stat.fsu.edu/~jfrade/HOMEWORKS/STA5166/hw4/mvtnorm/html/Mvnorm.html


library(mvtnorm)

#to get a prob dist , we need to define number of points, mean vector, cov matrix
sigma <- matrix(c(1,0,0,1),ncol=2)
mean=c(0,0)
randNums <- rmvnorm(n=5000,mean=mean,sigma=sigma,method="chol")
message("Mean of Columns")
colMeans(randNums)
message("variance of randNums")

message("Plot the Random Numbers")
plot(randNums)


