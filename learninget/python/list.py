# -*- coding: utf-8 -*-
from collections import Iterable

def changeDefaultPar(l = []):
    l.append("item")
    return l

print(changeDefaultPar())
print(changeDefaultPar())
print(changeDefaultPar())

def par(a,b,c=1,d=2,*kw):
	print('a=',a,'b=',b,'c=',c,'d=',d,'kw=',kw)

par('a','b',*(1,2,3))


def add(n):
	if n==1:
		return 1
	return n*(add(n-1))

print(add(5))

list = ['a','b','c']

print(list[::2])

for str in ['1','2','3']:
	print(str)

print(map(add,[1,2,3]))