import matplotlib.pyplot as plt
from matplotlib import style
import pandas as pd
import os
from os import listdir
from os.path import isfile, join
import shutil
import sys

style.use('ggplot')

curr_dir = sys.argv[0]
curr_dir = curr_dir.replace("\\","/")
curr_dir = "/".join(curr_dir.split("/")[:-1])
os.chdir(curr_dir)
mypath = "csv_files"
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]

newpath = "probs_graphs"
if not os.path.exists(newpath):
    os.makedirs(newpath)
else:
    try:
        shutil.rmtree(newpath)
    except Exception as a:
        print("The directory with previous graphs is under use. Failed to build new graphs")
        sys.exit()
    os.makedirs(newpath)

for file_csv in onlyfiles:
    name = file_csv[:-4]
    df = pd.read_csv(mypath + "/" +  file_csv, index_col = "position")
    ax = df.plot(title = "Probabilities for residues", legend = True, fontsize = 10)
    ax.set_xlabel("Position", fontsize = 14)
    ax.set_ylabel("Probability", fontsize = 14)

    fig = ax.get_figure()
    fig.savefig(newpath +"/" + name + ".png")



#plt.show()
    
        
    




