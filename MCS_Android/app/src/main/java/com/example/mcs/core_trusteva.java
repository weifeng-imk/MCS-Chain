package com.example.mcs;


import java.util.ArrayList;

class TrustEva
{
    public static Double WeightCal(Pay_t Pay)
    {
        Double res = 0.0;

        res = Pay.value;

        return res;
    }

    public static Double AverageFBCal(ArrayList<FB_t> Feedback, Double[] Weight, Integer NumofFB)
    {
        Double res = 0.0;
        Double DoubleTemp1 = 0.0;

        for (int loop = 0; loop < NumofFB; loop ++)
        {
            res += Feedback.get(loop).content.value * Weight[loop];
            DoubleTemp1 += Weight[0];
        }

        res = res/DoubleTemp1;
        return res;
    }

    public static Double DeviationCal (FB_t Feedback, FB_t AveFeedback)
    {
        Double res = 0.0;

        res = Math.abs(Feedback.content.value - AveFeedback.content.value);

        return res;
    }


    public static Double TrustEvaluation(ArrayList<FB_t> Feedback, ArrayList<Pay_t> Pay, TV_t TrustPast, Integer NumofFB, Integer Kcurr, Integer Kpast, Integer KofFB, Double Tau)
    {
        Double res = 0.0;

        Double res1 = 0.0;
        Double res2 = 0.0;

        Double[] Weight = new Double[NumofFB];
        //Double[] DoubleArr1DV = new Double[NumofFB];

        Double DoubleTemp1O = 1.0;
        Double DoubleTemp2 = 0.0;


        FB_t FBAve = new FB_t();

        for(int loop = 0; loop < NumofFB; loop ++)
        {
            Weight[loop] = WeightCal(Pay.get(loop));
        }

        FBAve.content.value = AverageFBCal(Feedback, Weight, NumofFB);

        for(int loop = 0; loop < NumofFB; loop ++)
        {
            DoubleTemp2 = DeviationCal(Feedback.get(loop), FBAve);

            DoubleTemp2 = 1.0 - DoubleTemp2;
            DoubleTemp2 *= Weight[loop];

            DoubleTemp1O += DoubleTemp2;

            DoubleTemp2 *= Feedback.get(loop).content.value;
            res1 += DoubleTemp2;
        }



        DoubleTemp2 = Math.abs(KofFB.doubleValue()-Kcurr.doubleValue());
        DoubleTemp2 = 0.0 - DoubleTemp2;
        DoubleTemp2 /= Tau;
        DoubleTemp2 = Math.exp(DoubleTemp2);
        res1  /= (DoubleTemp2 + 1);
        res1 /= DoubleTemp1O;

        res2 = DoubleTemp2 / (DoubleTemp2 + 1);
        res2 *= TrustPast.value;

        DoubleTemp2 = Math.abs(Kcurr.doubleValue()-Kpast.doubleValue());
        DoubleTemp2 = 0.0 -DoubleTemp2;
        DoubleTemp2 /= Tau;
        DoubleTemp2 = Math.exp(DoubleTemp2);

        res2 *= DoubleTemp2;

        res = res1 + res2;


        return res;
    }




}