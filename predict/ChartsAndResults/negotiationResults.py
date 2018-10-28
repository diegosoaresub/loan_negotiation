#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Jul  8 13:48:41 2018

@author: diegosoaresub
"""

#Juros Médio por grade
#Valor Médio por grade
#Quantidade de Negociações fechadas inadimplentes por grade
#Média de [Prob de Inadimplência] de [negociações fechadas] por grade
#Quantidade de Negóciações não fechadas por grade



import locale
import pandas as pd
from charts import PlotCharts

pd.set_option('max_columns', 120)
pd.set_option('max_colwidth', 5000)
pd.set_option('display.float_format', lambda x: locale.format('%.2f', x, grouping=True))
locale.setlocale(locale.LC_ALL, 'pt_br.utf-8')

results_proposed=pd.read_csv('../../history/result_ai.csv', low_memory=False)
results_ref=pd.read_csv('../../history/result_fixed.csv', low_memory=False)


plot_proposed = PlotCharts(results_proposed)
plot_ref = PlotCharts(results_ref)

print('\n\n########################################    Rounds   ########################################')
plot_ref.plotRoundsByGrade('Modelo de Haalema')
plot_proposed.plotRoundsByGrade('Modelo Proposto')

#plot_proposed.plotRoundsByGradeAndDefault()
plot_proposed.plotIntRateByGradeAndDefault()

plot_proposed.plotIntRateByGradeAndDeal()
plot_proposed.plotRoundsByGradeAndDeal()

print('\n\n########################################    Negociações Fechadas   ########################################')
plot_ref.plotNumNegotiationsByGrade('Modelo de Haalema')
plot_proposed.plotNumNegotiationsByGrade('Modelo Proposto')

print('\n\n######################################## Negociações Inadimplentes ########################################')
plot_ref.plotNegFechadasInadimplmentesByGrade('Modelo de Haalema')
plot_proposed.plotNegFechadasInadimplmentesByGrade('Modelo Proposto')


print('\n\n########################################    Juros - Negociacoes Fechadas   ########################################')
plot_ref.plotInteresRateByGradeNegociacoesFechadas('Modelo de Haalema')
plot_proposed.plotInteresRateByGradeNegociacoesFechadas('Modelo Proposto')


#negociacoes inadimplentes fechadas