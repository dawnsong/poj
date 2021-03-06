#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re
import sys,os
import ConfigParser
import logging
from time import sleep
import urllib,urllib2,cookielib
from BeautifulSoup import BeautifulSoup

#Extension of source filename
FILEEXT = {
        '.cpp':'g++',
        '.c':'gcc',
        '.pas':'pascal'
        }

class POJ:
    URL_HOME = 'http://poj.org/'
    URL_LOGIN = URL_HOME + 'login?'
    URL_SUBMIT = URL_HOME + 'submit?'
    URL_STATUS = URL_HOME + 'status?'
    URL_PROBLEM = URL_HOME + 'problem?'

    #结果信息
    INFO =['RunID','User','Problem','Result','Memory','Time','Language','Code Length','Submit Time']
    #语言
    LANGUAGE = {
            'G++':'0',
            'GCC':'1',
            'JAVA':'2',
            'PASCAL':'3',
            'C++':'4',
            'C':'5',
            'FORTRAN':'6',
            }
    def __init__(self, user_id, password):
        self.user_id = user_id
        self.password = password
        cj = cookielib.LWPCookieJar()
        self.opener =urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
        urllib2.install_opener(self.opener)

    def login(self):
        data = dict(
                user_id1 = self.user_id,
                password1 = self.password,
                B1 = 'login',
                url = '.')
        postdata = urllib.urlencode(data)
        try:
            req = urllib2.Request(POJ.URL_LOGIN,postdata)
            res = self.opener.open(POJ.URL_LOGIN,postdata).read()
            if res.find('loginlog')>0:
                logging.info("login successful!")
                return True
            else:
                logging.error('login failed')
                return False
        except:
            logging.error('login failed')
            return False

    def submit(self,pid,language,src):
        submit_data = dict(
                problem_id = pid,
                language = POJ.LANGUAGE[language.upper()],
                source = src,
                submit = 'Submit',)
        postdata2 = urllib.urlencode(submit_data)
        try:
            req2 = urllib2.Request(POJ.URL_SUBMIT,data = postdata2)
            res = self.opener.open(POJ.URL_SUBMIT,postdata2).read()
            logging.info('submit successful')
            return True
        except:
            logging.error('submit error')
            return False

    def problem(self, pid):
        url=POJ.URL_PROBLEM + urllib.urlencode({'id':pid})
        if not os.path.isfile('problem.htm'):
            page = urllib2.urlopen(url)
            with open('problem.htm','w') as problem:
                problem.write(page.read())

    def result(self,user_id):
        url = POJ.URL_STATUS + urllib.urlencode({'user_id':user_id})
        page = urllib2.urlopen(url)
        soup = BeautifulSoup(page)
        table = soup.findAll('table',{'class':'a'}) #提取表格
        pattern = re.compile(r'>[-+: \w]*<')  #正则表达式匹配需要的信息
        result = pattern.findall(str(table))
        #正在评判，编译或等待
        wait = ['Running & Judging','Compiling','Waiting']
        for i in range(3):
            if result[32][1:-1]==wait[i] or result[32][1:-1] == '':
                logging.info(result[32])
#                sleep(1)
                return False
        #最终结果在result序列中的位置
        num = [21,24,28,32,35,37,40,43,45]
        with open('result.log','w') as flog:
            for i in range(9):
                print POJ.INFO[i],':',result[num[i]][1:-1]
                flog.write('%s : %s\n' % (POJ.INFO[i],result[num[i]][1:-1]))
        return True


if __name__=='__main__':
    #read in username from cfg
    cfg=ConfigParser.ConfigParser()
    cfg.read('/home/dawnsong/.pojrc')
    ###user_id, pwd, pid, lang, src, = sys.argv[1:]
    user_id=cfg.get('poj', 'username','${4:-"username"}')
    pwd=cfg.get('poj', 'passwd','${4:-"passwd"}')

    #基础logging模块配置
    FORMAT = '----%(message)s----'
    logging.basicConfig(level=logging.INFO,format = FORMAT)
    if len(sys.argv) > 1: #从外部传入参数
        src = sys.argv[1]
        lang=''
        if len(sys.argv)==2:
            pid=os.path.basename(os.getcwd())
        elif len(sys.argv)==3:
            pid=sys.argv[2]
        else:
            src, pid, lang, = sys.argv[1:]
        if ''==lang:
            fn,fext = os.path.splitext(src)
            lang=FILEEXT[fext.lower()]
        print src, pid, lang
        #sys.exit()
        src = open(src,'r').read()
    else:  #测试
        pid = 1000
        lang = 'gcc'
        src = '''
        #include<stdio.h>
        int main()
        {
            int a,b;
            scanf("%d%d",&a,&b);
            printf("%d",a+b);
            return 0;
        }
        '''

    logging.info('connecting to server')
    poj = POJ(user_id,pwd)
    logging.info("Get problem && Login")
    poj.problem(pid)
    if poj.login():
        logging.info("submiting")
        if poj.submit(pid,lang,src):
            logging.info('getting result')
            status = poj.result(user_id)
            while status!=True:  #直到检测到结果
                status = poj.result(user_id)
