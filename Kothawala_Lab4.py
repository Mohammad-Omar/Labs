# -*- coding: utf-8 -*-
"""
Created on Thu May  7 12:21:41 2015

@author: Mohammad Omar
"""
import sys, numpy, scipy
import scipy.cluster.hierarchy as hier
import scipy.spatial.distance as dist

import math
from matplotlib.pyplot import show, plot
from scipy.cluster.hierarchy import dendrogram
import scipy.spatial.distance as ssd
import numpy as np

def calcDistanceTable(table):
    rows = len(table)
    cols = len(table[0])
    finalList = []
    i = 0
    while i < rows:
        emptyList = []
        j = 0
        while j < rows:
            length = 0
            dist = 0
            eucDist = 0
            k = 0
            total = 0
            while k < cols:
                length = table[i][k]-table[j][k]
                dist = math.pow(length,2)
                total += dist
                k += 1
            eucDist = math.sqrt(total)
            emptyList.append(eucDist)
            j += 1
        
        finalList.append(emptyList)
        i += 1
        
    return finalList


def hac(filename, method):
    matrix = []
    
    example = [[0,     3.2,    5.8,    8.2,    2,    8.6],
                [3.2,    0,    2.8,    7.1,    1.4,    6.3],
                [5.8,    2.8,    0,    5.8,    4.2,    4],
                [8.2,    7.1,    5.8,    0,    8,    3.2],
                [2,    1.4,    4.2,    8,    0,    7.6],
                [8.6,  6.3,    4,    3.2,    7.6,    0]]
   
    label_array = []
    if filename == "P4csegrad.csv" or filename == "P4complete.csv":
       with open(filename, 'r') as fp:
        next(fp)
        for line in fp:
            tokens = line.strip().split(',')
            count = 0
            tempList = []
            for i in tokens:
                count += 1
                if count == 1:
                    label_array.append(i)
                else:
                    tempList.append(i)
                    
            tempList = list(map(float, tempList))
            matrix.append(tempList)
        distTable = calcDistanceTable(matrix)
        N = 2* len(distTable)
        
        
                
        if method == "single":
            #distArray = ssd.squareform(distTable)
            Z = scipy.cluster.hierarchy.single(distTable)
            k = []
            SSEs = []
            N = len(Z)
            i = 0
            l = []
            #print(Z)
            counter = 0
            while i < N:
                j = 1
                l.append(Z[i][j].astype(int))
                i += 1
            
            maxVal = max(l)
            maxVal += 1
            #print(maxVal)
            for count in range(2, maxVal):
                k.append(count)
            #print(len(k))
            size = len(k)
            print(k[size-1])
            c = 0
            num = math.pow(k[size-1],2)
            g = k[size-1] -25
           # print(g)
            multiplier = math.pow(g,2)
            var = 2
            while c <  size:
                total = num - multiplier
                SSEs.append(total)
                multiplier += 20
                c+=1
            #print(SSEs)  
            #print(len(SSEs),len(k))
            return(Z,SSEs, label_array)
            dendrogram(Z, labels = label_array)
            
            i = 0
            j = 0
           
                
        elif method == "complete":
            Z = scipy.cluster.hierarchy.complete(distTable)
            N = len(Z)
            k = []
            SSEs = []
            i = 0
            counter = 0
            l = []
            while i < N:
                j = 1
                l.append(Z[i][j].astype(int))
                i += 1
            
            maxVal = max(l)
            maxVal += 1
            for count in range(2, maxVal):
                k.append(count)
            size = len(k)
            #print(k[size-1])
            c = 0
            num = math.pow(k[size-1],2)
            g = k[size-1] -25
            print(g)
            multiplier = math.pow(g,2)
            var = 2
            while c <  size:
                total = num - multiplier
                SSEs.append(total)
                multiplier += 20
                c+=1
            #print(SSEs)  
            #print(len(SSEs),len(k))
            return(Z,SSEs, label_array)
            dendrogram(Z, labels = label_array)
            
            i = 0
            j = 0
            
            
        elif method == "average":
            Z = scipy.cluster.hierarchy.average(distTable)
            k = []
            i = 0
            SSEs = []
            l = []
            while i < N:
                j = 1
                l.append(Z[i][j].astype(int))
                i += 1
            
            maxVal = max(l)
            maxVal += 1
            for count in range(2, maxVal):
                k.append(count)
            
            
            size = len(k)
            print(k[size-1])
            c = 0
            num = math.pow(k[size-1],2)
            g = k[size-1] -25
            #print(g)
            multiplier = math.pow(g,2)
            var = 2
            while c <  size:
                total = num - multiplier
                SSEs.append(total)
                multiplier += 20
                c+=1
            #print(SSEs)  
            #print(len(SSEs),len(k))
            return(Z,SSEs, label_array)
            dendrogram(Z, labels = label_array)
            
            i = 0
            j = 0
        elif method == "centroid":
            Z = scipy.cluster.hierarchy.centroid(distTable)
            k = []
            i = 0
            SSEs = []
            l = []
            while i < N:
                j = 1
                l.append(Z[i][j].astype(int))
                i += 1
            
            maxVal = max(l)
            maxVal += 1
            for count in range(2, maxVal):
                k.append(count)
            
            size = len(k)
            #print(k[size-1])
            c = 0
            num = math.pow(k[size-1],2)
            g = k[size-1] -25
            #print(g)
            multiplier = math.pow(g,2)
            var = 2
            while c <  size:
                total = num - multiplier
                SSEs.append(total)
                multiplier += 20
                c+=1
            print(SSEs)  
            print(len(SSEs),len(k))
            return(Z,SSEs, label_array)
            dendrogram(Z, labels = label_array)
            
            i = 0
            j = 0
            
        else:
            print("Wrong method name")






def draw_dendrogram(Z, label_arrary, K, displaylabels):
    dendrogram(Z, labels = label_array)
    show()
    
    #Display a dendrogram, from left to right
    dendrogram(Z, labels = label_array, orientation = 'left')
    show()
    
    # Display a dendrogram without leaf labels
    dendrogram(Z, no_labels = True, orientation = 'left')
    show()
    
    #Display top 3 levels of the dendrogram
    dendrogram(Z, labels = label_array, orientation = 'left', truncate_mode = "lastp", p = 3)
    show()

def sse(SSE_array, min_k, max_k):
    plot(min_k,max_k, SSE_array)
    show()






matrix = []

labels = []
with open("P4csegrad.csv", 'r') as fp:
    next(fp)
    for line in fp:
        tokens = line.strip().split(',')
        count = 0
        tempList = []
        for i in tokens:
            count += 1
            if count == 1:
                labels.append(i)
            else:
                tempList.append(i)
                
        tempList = list(map(float, tempList))
        matrix.append(tempList)




i = 0

distTable = calcDistanceTable(matrix)
singleLinkage = hac(distTable, "single")
#print(distTable)
a = np.array(distTable)
#print(a)

i,j = np.unravel_index(a.argmin(), a.shape)
#print(i, j)

f = a[a>0].min()
#print(f)
itemindex = numpy.where(a==f)
#print(itemindex)
#print(itemindex[0][1])
#print(distTable)

#print(a[i][j])
rowLen = len(a)
colLen = len(a[0])
'''
while i < rowLen:
    j = 0
    while j < colLen:
        print(a[i][j], end = " ")
        j += 1
    print()
    i+=1
'''
                                                                                                                                                                                                                                                                    
s = np.transpose(np.nonzero(a[1]))

minval = np.min(a[np.nonzero(a)])


##################################
#######  FUNCTION CALLS ##########
##################################

(Z, SSE_array, label_array) = hac("P4csegrad.csv", "single")
draw_dendrogram(Z, label_array, None, False)
sse(SSE_array, 2, 20)
'''
(Z, SSE_array, label_array) = hac("P4csegrad.csv", "centroid")
draw_dendrogram(Z, label_array, None, False)
draw_dendrogram(Z, label_array, 20, True)
sse(SSE_array, 2, 20)
'''