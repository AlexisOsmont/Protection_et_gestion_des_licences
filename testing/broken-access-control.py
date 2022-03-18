import requests
import warnings
import os
import sys
import re

CONST_SERVER_URL = 'https://srv-dpi-proj-gestlic-web.univ-rouen.fr:8443/'

def usage():
    print('usage: python ' + sys.argv[0] + ' /path/to/web.xml')
    exit()

def GET(url):
    with warnings.catch_warnings():
        warnings.simplefilter("ignore")
        return requests.get(url, verify=False)

def POST(url, data):
    with warnings.catch_warnings():
        warnings.simplefilter("ignore")
        return requests.post(url, data=data, verify=False)

def main():
    if (len(sys.argv) != 2):
        usage()
    
    routes = []
    try:
        fd = open(sys.argv[1], 'r')
        routes = list(map(
            lambda x : x.replace('url-pattern>', '').replace('</url-pattern', ''), 
            re.findall('url-pattern>.*</url-pattern', fd.read()))
        )
    except:
        print('Error: failed to open ' + sys.argv[1])
        exit() 
    
    for url in routes:
        response = GET(CONST_SERVER_URL + url)
        print('Testing ' + CONST_SERVER_URL + url)

        padding = ' ' * 2
        for hist in response.history:
            print(padding + hist.request.method + ' ' + hist.request.url)
            print(padding + str(hist.status_code) + ' ' + hist.url)

        print(padding + response.request.method + ' ' + response.request.url)
        print(padding + str(response.status_code) + ' ' + response.url) 
        print('')

if __name__ == '__main__':
    main()
