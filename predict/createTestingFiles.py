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
        'grade',
        'annual_inc',
        'loan_amnt'
]];

loans_defaulted = loans[loans.loan_status == 0];
loans_ok = loans[loans.loan_status == 1]

