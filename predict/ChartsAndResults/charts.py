# -*- coding: utf-8 -*-


import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


class PlotCharts:
    
    def __init__(self, data):
        self.data = data    
    
    
    def plotGenericTwoLinesChart(self, title, line_one, line_two, loc_one=None, loc_two=None):
        # Create a legend for the first line.
        first_legend = plt.legend(handles=[line_one], loc=2 if loc_one is None else loc_one)
        
        # Add the legend manually to the current Axes.
        plt.gca().add_artist(first_legend)
        
        # Create another legend for the second line.
        plt.legend(handles=[line_two], loc=4 if loc_two is None else loc_two)
        
        plt.title(title, color='black')
        plt.show()


    def plotGenericDefaultChart(self, title, _values, _index, _columns, agg, loc_one=None, loc_two=None):

        result = pd.pivot_table(self.data, values=_values, index=_index,
                                        columns=_columns, aggfunc=agg)#, fill_value=0)
        result.reindex_axis(sorted(result.columns), axis=1)
        
        line_ok, = plt.plot(result.iloc[:,0], label="Adimplente", linewidth=4)
        line_default, = plt.plot(result.iloc[:,1], label="Inadimplente", linestyle='--', color='red')
        
        self.plotGenericTwoLinesChart(title, line_ok, line_default, loc_one, loc_two)
        
        
    def plotGenericDealtChart(self, title, _values, _index, _columns, agg, loc_one=None, loc_two=None):

        result = pd.pivot_table(self.data, values=_values, index=_index,
                                        columns=_columns, aggfunc=agg, fill_value=0)
        result.reindex_axis(sorted(result.columns), axis=1)
        
        line_default, = plt.plot(result.iloc[:,0], label="Não Acordo", linestyle='--', color='red')
        line_ok, = plt.plot(result.iloc[:,1], label="Acordo", linewidth=4)
        
        plt.xlabel('Score')
        self.plotGenericTwoLinesChart(title, line_ok, line_default, loc_one, loc_two)


    def plotNumNegotiationsByGrade(self, title=''):
        line_deal = self.data.groupby(['CUSTOMER_GRADE'])['NEGOTIATION_DEAL'].sum()
        line_total = self.data.groupby(['CUSTOMER_GRADE'])['NEGOTIATION_DEAL'].count()
        
        plt.figure(figsize=(7,5))

        line_total, = plt.plot(line_total, label="Total de Negociações")
        line_deal, = plt.plot(line_deal, label="Houve Acordo", linestyle='--', color='red')
        
        plt.xlabel('Score')
        plt.ylim(40, 102)
        self.plotGenericTwoLinesChart(title, line_total, line_deal, 3, 4)

    def plotNegFechadasInadimplmentesByGrade(self, title=''):
        
        data_deal = self.data[self.data.NEGOTIATION_DEAL == 1]
        
        line_deal = data_deal.groupby(['CUSTOMER_GRADE'])['NEGOTIATION_DEFAULT'].sum()
        line_total = data_deal.groupby(['CUSTOMER_GRADE'])['NEGOTIATION_DEFAULT'].count()
        
        plt.figure(figsize=(7,5))
        
        line_total, = plt.plot(line_total, label="Total")
        line_deal, = plt.plot(line_deal, label="Indimplente", linestyle='--', color='red')
        
        plt.xlabel('Score')
        plt.ylim(0, 102)        
        self.plotGenericTwoLinesChart(title, line_total, line_deal, 4, 3)


        
    def plotRoundsByGradeAndDefault(self):
        plt.figure(figsize=(7,5))
        plt.xlabel('Score')
        plt.ylim(0, 11)
        self.plotGenericDefaultChart('Rodadas x Inadimplência', ['NEGOTIATION_ROUNDS'], ['CUSTOMER_GRADE'], ['NEGOTIATION_DEFAULT'], np.mean, 1, 2)

    def plotRoundsByGrade(self, title):
        plt.figure(figsize=(7,5))
        plt.title(title, color='black')
        plt.xlabel('Score')
        plt.ylim(0, 11)
        
        result = pd.pivot_table(self.data, values=['NEGOTIATION_ROUNDS'], index=['CUSTOMER_GRADE'], aggfunc=np.mean)
        result.reindex_axis(sorted(result.columns), axis=1)
        
        plt.plot(result.iloc[:,0], linewidth=4)
        

    def plotInteresRateByGradeNegociacoesFechadas(self, title):
        plt.figure(figsize=(7,5))
        plt.title(title, color='black')
        plt.xlabel('Score')
        plt.ylim(0, 25)
        
        data_deal = self.data[self.data.NEGOTIATION_DEAL == 1]
        
        result = pd.pivot_table(data_deal, values=['LAST_INT_RATE_PROPOSAL'], index=['CUSTOMER_GRADE'], aggfunc=np.mean)
        result.reindex_axis(sorted(result.columns), axis=1)
        
        print(result)
        
        plt.plot(result.iloc[:,0], linewidth=4)

        

    def plotIntRateByGradeAndDefault(self):
        plt.figure(figsize=(7,5))
        plt.xlabel('Score')
        plt.ylim(0, 27)
        self.plotGenericDefaultChart('Juros x Inadimplência', ['LAST_INT_RATE_PROPOSAL'], ['CUSTOMER_GRADE'], ['NEGOTIATION_DEFAULT'], np.mean, 1, 2)
       
        
    def plotIntRateByGradeAndDeal(self):
        plt.figure(figsize=(7,5))
        plt.xlabel('Score')
        plt.ylim(0, 27)
        self.plotGenericDealtChart('Juros x Acordo', ['LAST_INT_RATE_PROPOSAL'], ['CUSTOMER_GRADE'], ['NEGOTIATION_DEAL'], np.mean, 1, 2)
        
    def plotRoundsByGradeAndDeal(self):
        plt.figure(figsize=(7,5))
        plt.xlabel('Score')
        plt.ylim(0, 14)
        self.plotGenericDealtChart('Rodadas x Acordo', ['NEGOTIATION_ROUNDS'], ['CUSTOMER_GRADE'], ['NEGOTIATION_DEAL'], np.mean, 1, 2)
        