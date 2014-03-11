import operator
import string

terms = {}

f = open("result.txt", 'rb')
ln = 0
for line in f:
  if len(line.strip()) == 0: continue
  if ln == 0:
    # make {id,term} dictionary for use later
    tn = 0
    for term in line.strip().split(","):
      terms[tn] = term
      tn += 1
  else:
    # parse out topic and probability, then build map of term to score
    # finally sort by score and print top 10 terms for each topic.
    topic, probs = line.strip().split("\t")
    termProbs = {}
    pn = 0
    for prob in probs.split(","):
      termProbs[terms[pn]] = float(prob)
      pn += 1
    toptermProbs = sorted(termProbs.iteritems(),
      key=operator.itemgetter(1), reverse=True)
    print "Topic: %s" % (topic)
    print "\n".join([(" "*3 + x[0]) for x in toptermProbs[0:10]])
  ln += 1
f.close()

