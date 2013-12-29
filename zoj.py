# -*- coding: utf-8 -*-
import re
import sys
import logging
from time import sleep
import urllib,urllib2,cookielib
from BeautifulSoup import BeautifulSoup

class ZOJ:
    URL_HOME = 'http://acm.zju.edu.cn/onlinejudge/'
    URL_LOGIN = URL_HOME + 'login.do?'
    URL_SUBMIT = URL_HOME + 'submit.do?'
    URL_STATUS = URL_HOME + 'showRuns.do?contestId=1&'
    
    #结果信息
    INFO =['RunID','Submit Time','Judge Status','Problem ID',
        'Language','Run Time(ms)','Run Memory(KB)','User Name']
    #语言：为了防止出错，gcc定义为C语言，g++定义为c++，zoj没有gcc和g++选项
    LANGUAGE = {
            'C':'1',
            'C++':'2',
            'FPC':'3',
            'JAVA':'4',
            'PYTHON':'5',
            'PERL':'6',
            'SCHEME':'7',
            'PHP':'8',
            'GCC':'1',
            'G++':'2',
            }

    def __init__(self, user_id, password):
        self.user_id = user_id
        self.password = password
        cj = cookielib.LWPCookieJar()
        self.opener =urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
        urllib2.install_opener(self.opener)

    def login(self):
        data = dict(
                handle = self.user_id,
                password = self.password,
                )
        postdata = urllib.urlencode(data)
        try:
            req = urllib2.Request(ZOJ.URL_LOGIN,postdata)
            res = self.opener.open(ZOJ.URL_LOGIN,postdata).read()
            if res.find(self.user_id)>0:
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
                problemId = str(int(pid) - 1000),
                languageId = ZOJ.LANGUAGE[language.upper()],
                source = src,)
        postdata2 = urllib.urlencode(submit_data)
        try:
            req2 = urllib2.Request(ZOJ.URL_SUBMIT,data = postdata2)
            res = self.opener.open(ZOJ.URL_SUBMIT,postdata2).read()
            logging.info('submit successful')
            return True
        except:
            logging.error('submit error')
            return False

    def result(self,user_id):
        url = ZOJ.URL_STATUS + urllib.urlencode({'handle':user_id})
        page = urllib2.urlopen(url)
        soup = BeautifulSoup(page)
        table = soup.findAll('table',{'class':'list'})
        table = ''.join(str(table).split())
        pattern = re.compile(r'>[-+:\w]*<')
        result = pattern.findall(str(table))
        wait = ['Running','Compiling','Waiting']
        num = [18,20,23,27,31,34,36,40]
        for i in range(3):
            if result[23][1:-1]==wait[i]:
                logging.info(result[23])
                sleep(1)
                return False
        if result[23][1:-1] == '':
            num = [18,20,24,29,33,36,38,42]
        for i in range(8):
            print ZOJ.INFO[i],':',result[num[i]][1:-1]
        return True


if __name__=='__main__':
    FORMAT = '----%(message)s----'
    logging.basicConfig(level=logging.INFO,format = FORMAT)
    if len(sys.argv) > 1:
        user_id, pwd, pid, lang, src, = sys.argv[1:]
        src = open(src,'r').read()
    else:
        user_id = 'username'
        pwd = 'password'
        pid = 1001
        lang = 'c++'
        src = '''
        #include<stdio.h>
        int main()
        {
            int a,b;
            while(scanf("%d%d",&a,&b)!=EOF)
            printf("%d\n",a+b);
            return 0;
        }
        '''
    logging.info('connecting to server')
    zoj = ZOJ(user_id,pwd)
    if zoj.login():
        logging.info('submiting')
        if zoj.submit(pid,lang,src):
            logging.info('getting result')
            status = zoj.result(user_id)
            while status!=True:
                status = zoj.result(user_id) 
