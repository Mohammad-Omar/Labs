# -*- coding: utf-8 -*-
"""
Created on Fri Feb 20 22:19:30 2015

@author: Mohammad Kothawala
"""

import os
import math
from collections import Counter
from nltk.corpus import stopwords
from nltk.stem.porter import PorterStemmer
from nltk.tokenize import RegexpTokenizer
stopset = sorted(stopwords.words('english'))
temp = {}
d = {}
dIDF = {}
corpus_root = 'C:\\Users\\Mohammad Omar\\Desktop\\DataMiningCSE4331\\stateoftheunionaddresses'

def query(qstring):
    tokenizer = RegexpTokenizer(r'[a-zA-Z]+')
    tokens = tokenizer.tokenize(qstring)
    tokens = [x.lower() for x in tokens]
    tokens = [w for w in tokens if not w in stopset]
    bList = []
    stemmer = PorterStemmer()
    for items in tokens:
        bList.append(stemmer.stem(items))
    occurences = Counter(bList)
    qDict = {}
    dComp = {}
    qDict = dict(occurences)
    size = len(qDict) 
    agg = 0
    normQuerySum = 0 
    
    for term in qDict:
        tf = math.log10(qDict[term]) + 1  
        idf = math.log10(float(size/1))
        qDict[term] = tf * idf
    for s in qDict:
        x = qDict[s]
        sq = math.pow(x,2)
        agg += sq
    leng = math.sqrt(agg)
    for phrase in qDict:
        norm = (float)(qDict[phrase]/leng) 
        qDict[phrase] = norm 
        normQuerySum += norm

    for filename in d:
        compareSum = 0
        for term in qDict:
            if term in (d[filename].keys()):
                compareSum += d[filename][term]
            else:
                compareSum += 0
        
        dComp[filename] = normQuerySum - compareSum

    b = dict(map(lambda item: (item[1],item[0]),dComp.items()))
    min_key = b[min(b.keys())]
    return min_key

def gettfidfvec(filename):
    if filename in d.keys():
        return(d[filename])
    else:
        print("does not exist")
        
def getidf(token):
    if token in temp.keys():
        IDF = math.log10(float(229/temp[token]))
    else:
        IDF = 0
    return IDF
    
def docdocsim(filename1,filename2):
    sim = 0 
    dotProd = 0
    for word in d[filename1]:
        if word in (d[filename2].keys()):
            dotProd = d[filename1][word] * d[filename2][word]  
        sim += dotProd
        dotProd = 0
    return sim   
    
def querydocsim(qstring,filename):
    tokenizer = RegexpTokenizer(r'[a-zA-Z]+')
    tokens = tokenizer.tokenize(qstring)
    tokens = [x.lower() for x in tokens]
    tokens = [w for w in tokens if not w in stopset]
    bList = []
    stemmer = PorterStemmer()
    for items in tokens:
        bList.append(stemmer.stem(items))
    occurences = Counter(bList)
    queryDict = {}
    queryDict = dict(occurences)
    size = len(queryDict) 
    agg = 0
    normQuerySum = 0 
    
    for term in queryDict:
        tf = math.log10(queryDict[term]) + 1  
        idf = math.log10(float(size/1))
        queryDict[term] = tf * idf
    #print(qDict)
    for s in queryDict:
        x = queryDict[s]
        sq = (float)(math.pow(x,2))
        agg += sq
    leng = (float)(math.sqrt(agg))
    for phrase in queryDict:
        norm = (float)(queryDict[phrase]/leng) 
        queryDict[phrase] = norm    
    sim = 0 
    dotProd = 0
    for word in queryDict:
        
        if word in (d[filename].keys()):
            dotProd = d[filename][word] * queryDict[word]
        sim += dotProd
        dotProd = 0
    return sim  

def getNormTFIDF():
    global d
    for docs in d:
        x = 0
        total = 0
        length = 0
        for term in d[docs]:
            x = d[docs][term]
            square = math.pow(x,2)
            total += square
        length = math.sqrt(total)
        for phrase in d[docs]:
            norm = (float)(d[docs][phrase]/length) 
            d[docs][phrase] = norm
            
     

    
########################################################
######################## BEGIN #########################
#################### MAIN FUNCTION #####################
#################### MAIN FUNCTION #####################
#################### MAIN FUNCTION #####################
######################## BEGIN #########################
########################################################

for filename in os.listdir(corpus_root):
    file = open(os.path.join(corpus_root, filename), "r")
    doc = file.read()
    tokenizer = RegexpTokenizer(r'[a-zA-Z]+')
    tokens = tokenizer.tokenize(doc)
    tokens = [x.lower() for x in tokens]
    tokens = [w for w in tokens if not w in stopset]
    file.close()
    aList = []
    
    stemmer = PorterStemmer()
    for items in tokens:
        aList.append(stemmer.stem(items))
    count = Counter(aList)
    d[filename] = dict(count)

            
for fileName in d:
    for word in d[fileName]: 
        documentCounter = 0
        if word not in temp.keys():
            documentCounter += 1
            
            for files in d:
                if files != fileName:
                    if word in d[files].keys():
                        documentCounter += 1
            temp[word] = documentCounter
          
                   
                
for docs in d:
        for term in d[docs]:
            tf = (float)(math.log10(d[docs][term])) + 1  
            idf = getidf(term)
            d[docs][term] = tf * idf

getNormTFIDF()


print(query("health insurance wall street")) 
print(gettfidfvec("Barack ObamaJanuary 20, 2015.txt"))
print(getidf("health"))     
print(docdocsim("Barack ObamaJanuary 20, 2015.txt", "Barack ObamaJanuary 28, 2014.txt"))        
print(querydocsim("health insurance wall street", "Barack ObamaJanuary 28, 2014.txt"))  

         
########################################################
######################### END ##########################
#################### MAIN FUNCTION #####################
#################### MAIN FUNCTION #####################
#################### MAIN FUNCTION #####################
######################### END ##########################
########################################################     
            
    
    
    