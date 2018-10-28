#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 14 21:26:05 2018

@author: diegosoaresub
"""

import pandas as pd

class Proposal:
    
    
    @staticmethod
    def calculateNextProposal(predict,
                              limit,
                              proposal_data,
                              round,
                              fixedLimits):

        print("calculateNextProposal - Begin")

        print("Grade: {} | Annual Income: {}".format(proposal_data['grade'], 
                                                     proposal_data['annual_inc']))

        
        i = 1
        while True:
            
            print("Generating Proposal {}".format(i))
            
            print("\tint_rate: [{}] loan_amnt:[{}]".format(proposal_data['int_rate'], 
                                                           proposal_data['loan_amnt']))
            
            
            df = pd.DataFrame(proposal_data, index=[0])
            df = df[[
                    'int_rate', 
                     'tot_cur_bal', 
                     'grade', 
                     'dti', 
                     'revol_bal', 
                     'revol_util', 
                     'annual_inc', 
                     'loan_amnt', 
                     'total_acc'
                     ]]
            
            
            print(df)
            
            prob_result = predict.predictDefault(df)
            predicted_class = prob_result['predicted_class']
            probas_classes = prob_result['probas_classes']
            
            print("\tDefault: {} [{}%]".format("NO" if predicted_class[0].item() == 1 else "YES",
                                              probas_classes[0][0].item()*100))

            #Modify to accapt at least 10% of default rate
            if predicted_class[0].item() == 1:
                break
            
            #Pegar a menor proposta que seja adimplente
            if proposal_data['int_rate'] < fixedLimits['int_rate']['min'] or \
                proposal_data['loan_amnt'] < fixedLimits['loan_amnt']['min']:

                if round == 1:
                  print("Trying to find a proposal with max interest rate...")
                  
                  proposal_data['int_rate'] = fixedLimits['int_rate']['max'] * 1.3
                  
                  return Proposal.calculateNextProposal(predict,
                                                        limit,
                                                        proposal_data,
                                                        0,
                                                        fixedLimits)
                else:
                  print("NO PROPOSAL FOUND")
                  proposal_data['int_rate'] = -1
                  proposal_data['int_rate'] = -1
                  break;
            
            proposal_data['int_rate'] = \
                    proposal_data['int_rate'] - proposal_data['int_rate'] * 0.03            
            i = i + 1


        print("calculateNextProposal - End")

        
        return {
                "int_rate": proposal_data['int_rate'],
                "loan_amnt": proposal_data['loan_amnt']
               }
        
#    @staticmethod
#    def calculateNextProposal(predict,
#                              limit,
#                              customer_grade,
#                              customer_annual_inc,
#                              customer_dti,
#                              customer_revol_bal,
#                              customer_revol_util,
#                              customer_tot_cur_bal,
#                              customer_total_acc,
#                              proposal_installments,
#                              proposal_loan_amount,
#                              proposal_interest_rate,                              
#                              round,
#                              fixedLimits):
#
#        print("calculateNextProposal - Begin")
#
#        print("Grade: {} | Annual Income: {}".format(customer_grade, 
#                                                  customer_annual_inc))
#
#        
#        i = 1
#        while True:
#            
#            print("Generating Proposal {}".format(i))
#            
#            print("\tint_rate: [{}] loan_amnt:[{}]".format(proposal_interest_rate, 
#                                                           proposal_loan_amount))
#            
#            
#            df = pd.DataFrame([[proposal_interest_rate, customer_grade, 
#                           customer_annual_inc, proposal_loan_amount, customer_dti, 
#                           customer_revol_bal, customer_revol_util, 
#                           customer_tot_cur_bal, customer_total_acc]], 
#                          columns=['int_rate', 'grade', 'annual_inc', 
#                                   'loan_amnt', 'dti', 'revol_bal',
#                                   'revol_util','tot_cur_bal', 'total_acc'])
#            
#            
#            print(df)
#            
#            prob_result = predict.predictDefault(df)
#            predicted_class = prob_result['predicted_class']
#            probas_classes = prob_result['probas_classes']
#            
#            print("\tDefault: {} [{}%]".format("NO" if predicted_class[0].item() == 1 else "YES",
#                                              probas_classes[0][0].item()*100))
#
#            #Modify to accapt at least 10% of default rate
#            if predicted_class[0].item() == 1:
#                break
#            
#            #Pegar a menor proposta que seja adimplente
#            if proposal_interest_rate < fixedLimits['int_rate']['min'] or \
#                proposal_loan_amount < fixedLimits['loan_amnt']['min']:
#
#                if round == 1:
#                  print("Trying to find a proposal with max interest rate...")
#                  
#                  proposal_interest_rate = fixedLimits['int_rate']['max'] * 1.3
#
#                  return Proposal.calculateNextProposal(predict,
#                                                        limit,
#                                                        customer_grade,
#                                                        customer_annual_inc,
#                                                        customer_dti,
#                                                        customer_revol_bal,
#                                                        customer_revol_util,
#                                                        customer_tot_cur_bal,
#                                                        customer_total_acc,
#                                                        proposal_installments,
#                                                        proposal_loan_amount,
#                                                        proposal_interest_rate,
#                                                        0,
#                                                        fixedLimits)
#                else:
#                  print("NO PROPOSAL FOUND")
#                  proposal_interest_rate = -1
#                  proposal_loan_amount = -1
#                  break;
#            
#            proposal_interest_rate = \
#                    proposal_interest_rate - proposal_interest_rate * 0.03            
#            i = i + 1
#
#
#        print("calculateNextProposal - End")
#
#        
#        return {
#                "int_rate": proposal_interest_rate,
#                "loan_amnt": proposal_loan_amount,
#                "installments": proposal_installments
#               }        