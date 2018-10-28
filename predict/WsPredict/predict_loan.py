#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 14 23:43:27 2018

@author: diegosoaresub
"""

from sklearn.externals import joblib

class PredictLoan:
    
    def __init__(self):
        print('Loading Gradient Boosting Model...')
        self.clf = joblib.load('gradient_boosting_model_9_features.pkl') 
    
    
    def predictDefault(self, user_info):
    
        result = dict()
        result['predicted_class'] = self.clf.predict(user_info)
        result['probas_classes'] = self.clf.predict_proba(user_info)                
        return result