#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 14 21:41:01 2018

@author: diegosoaresub
"""

import pandas as pd
import numpy as np


class Limits:
    
    BASE_RATE = 5.05
    
    def __init__(self):
        self.int_rate_table = {
        	'A': {'min': 1.06,  'max':  3.41},
        	'B': {'min': 5.03,  'max':  7.68},
        	'C': {'min': 8.51,  'max': 11.86},
        	'D': {'min': 12.92, 'max': 17.30},
        	'E': {'min': 18.35, 'max': 22.22},
        	'F': {'min': 23.67, 'max': 25.70},
        	'G': {'min': 25.74, 'max': 25.94}
        }
        
        print('Loading loans file...')
        
        self.loans = pd.read_csv('../loan_2007_2016Q4_old.csv', low_memory=False)
        self.loans = self.loans[[
            'loan_status',
            'int_rate',
            'grade',
            'annual_inc',
            'loan_amnt'
        ]];
        
        self.loans_defaulted = self.loans[self.loans.loan_status == 0];
        self.loans_ok = self.loans[self.loans.loan_status == 1]

    
    # https://www.lendingclub.com/foliofn/rateDetail.action
    def calculateFixedLimits(self, grade_num, annual_income):        
        
        grade = chr(65 + grade_num - 1)
        
        int_rate_min = Limits.BASE_RATE + np.nan_to_num(self.int_rate_table[grade]['min'])
        int_rate_max = Limits.BASE_RATE + np.nan_to_num(self.int_rate_table[grade]['max'])
        
        result = self.loans_ok[(self.loans.grade == grade_num) & 
                          (self.loans.annual_inc <= annual_income)].describe()
        
        result['int_rate']['min'] = int_rate_min
        result['int_rate']['max'] = int_rate_max
        
        return result

    

    def calculateLimits(self, grade, annual_income):        
        return self.loans_ok[(self.loans.grade == grade) & 
                          (self.loans.annual_inc <= annual_income)].describe()
