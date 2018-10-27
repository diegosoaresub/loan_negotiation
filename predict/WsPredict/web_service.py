import numpy as np
import pandas as pd
import json

from limits import Limits
from predict_loan import PredictLoan
from flask import Flask, request, jsonify
from proposal import Proposal

app = Flask(__name__)

BASE_RATE = 5.05

limits = Limits()
predict = PredictLoan()

print('Service Started...')

@app.route("/nextProposal", methods=['GET', 'POST'])
def nextProposal():    
    request_content = request.get_json()
    
    print("Request: \n", json.dumps(request_content, indent=2))

    customer_grade = int(request_content['grade'])
    
    fixedLimits = limits.calculateFixedLimits(customer_grade,  request_content['annual_inc'])
    
    print("Limits:")
    print("\tInterest Rate: {} - {}".format(fixedLimits['int_rate']['min'],fixedLimits['int_rate']['max']))
    print("\tAmount: {} - {}\n".format(fixedLimits['loan_amnt']['min'],fixedLimits['loan_amnt']['max']))


    result = Proposal.calculateNextProposal(predict, limits, customer_grade, request_content['annual_inc'], 
                                            request_content['installments'], request_content['loan_amnt'],
                                            request_content['int_rate'], request_content['round'], fixedLimits)
    
    
        
    return jsonify(
                int_rate=result["int_rate"],
                loan_amnt=result["loan_amnt"],
                installments=result["installments"]                
            )


@app.route("/calculateFixedLimits", methods=['GET', 'POST'])
def calculateFixedLimits():    

    request_content = request.get_json()
    print("Request: \n", json.dumps(request_content, indent=2))
    
    print("Grade: ", request_content['grade'])
    
    result = limits.calculateFixedLimits(request_content['grade'], request_content['annual_income'])
    
    print("Interest Rate: {} - {}".format(np.nan_to_num(result['int_rate']['min']), 
                                          np.nan_to_num(result['int_rate']['max'])))
    print("Amount       : {} - {}".format(np.nan_to_num(result['loan_amnt']['min']), 
                                          np.nan_to_num(result['loan_amnt']['max'])))
    
    response = jsonify(
        min_int_rate=np.nan_to_num(result['int_rate']['min']),
        max_int_rate=np.nan_to_num(result['int_rate']['max']),
        min_amount=np.nan_to_num(result['loan_amnt']['min']),
        max_amount=np.nan_to_num(result['loan_amnt']['max'])
    )      
    return response


@app.route("/calculateLimits", methods=['GET', 'POST'])
def calculateLimits():
    
    request_content = request.get_json()
    
    print("Request: \n", json.dumps(request_content, indent=2))
    
    result = limits.calculateLimits(request_content['grade'], request_content['annual_income'])
        
    response = jsonify(
        min_int_rate=np.nan_to_num(result['int_rate']['min']),
        max_int_rate=np.nan_to_num(result['int_rate']['max']),
        min_amount=np.nan_to_num(result['loan_amnt']['min']),
        max_amount=np.nan_to_num(result['loan_amnt']['max'])
    )
    
    return response


@app.route("/predictDefault", methods=['GET', 'POST'])
def predictDefault():

    content = request.get_json()
    
    print("Request: \n", json.dumps(content, indent=2))
    
    df = pd.DataFrame(content, index=[0])
    df = df[[
            'int_rate', 
#             'tot_cur_bal', 
             'grade', 
#             'dti', 
#             'revol_bal', 
#             'revol_util', 
             'annual_inc', 
             'loan_amnt', 
#             'total_acc'
             ]]
                 
    
    result = predict.predictDefault(df)
    
    predicted_class = result['predicted_class']
    probas_classes = result['probas_classes']
    
    print('Predicted class: {0}'.format(result['predicted_class']))
    print('Probabilities per class: {0}'.format(result['probas_classes']))

    
    return jsonify(
        predicted_class=("good" if predicted_class[0].item() == 1 else "delinquent"),
        delinquent_prob=probas_classes[0][0].item()*100
    )



if __name__ == "__main__":
    app.run()