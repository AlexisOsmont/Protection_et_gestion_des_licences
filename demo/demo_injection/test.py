import pefile

exe_path = "main.exe"

pe = pefile.PE(exe_path)

print(pe.dump_info());