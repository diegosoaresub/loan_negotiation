# -*- coding: utf-8 -*-

import pandas as pd
import locale

pd.set_option('max_columns', 120)
pd.set_option('max_colwidth', 5000)
pd.set_option('display.float_format', lambda x: locale.format('%.2f', x, grouping=True))

locale.setlocale(locale.LC_ALL, 'pt_br.utf-8')

loans = pd.read_csv('loan_2007_2016Q4_old.csv', low_memory=False)


loans = loans[[
        'loan_status',
        'int_rate',
#        'tot_cur_bal',
        'grade',
#        'dti',
#        'revol_bal',
#        'revol_util',
        'annual_inc',
        'loan_amnt',
#        'total_acc'
]];

loans_defaulted = loans[loans.loan_status == 0];
loans_ok = loans[loans.loan_status == 1]

_grade = 1
_annual_inc = 20000


result = loans_ok[(loans.grade == _grade) & (loans.annual_inc <= _annual_inc) ].describe()