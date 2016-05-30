# Evolutionary Computation Practical Assignment

Kieren Davies (DVSKIE001)

# Results

One sample of 100 runs achieved an average minimum of 4253.0 and an absolute minimum of 3494.

# Comparison

Comparison was done against results from Calvin Brizzi (BRZCAL001), which achieved an average minimum of 4267.2 and an absolute minimum of 3868.

Statistical comparison was done with a Mann–Whitney–Wilcoxon test, which is a nonparametric test between two samples, with a chosen p-value of 0.05.  The test yielded p = 0.8671 > 0.05, so we do not reject the null hypothesis.  Thus we conclude that the two algorithms produce equivalent results.

The most likely explanation for this is that the two algorithms use similar or identical strategies, performing only mutations on the best candidates.

An alternative but less likely explanation is that they are both close to the theoretical optimum that can be achieved in 100 generations, therefore they do not differ much and the results are dominated by random effects.  This can be remedied by testing larger samples.
