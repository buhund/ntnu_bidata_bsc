# IMAT2024 - Prosjekt - Vedlegg

Vedlegg med kode tilhørende de ulike oppgavene i prosjektet.



### Del 1, Oppgave 1b-iii

Logaritme-funksjon

```python
# Plott av den logaritmiske funksjonen
import numpy as np
import matplotlib.pyplot as plt

def v_log(u, v_max, u_max):
    """
    Logaritmisk hastighetsfunksjon.

    Args:
        u: Tetthet (array eller enkeltverdi).
        v_max: Maksimal hastighet.
        u_max: Maksimal tetthet.

    Returns:
        Hastighet (array eller enkeltverdi).
    """
    if isinstance(u, (int, float)):
        if u >= u_max:
            return 0 
        else:
            return v_max * np.log((u_max + 1) / (u + 1)) / np.log(u_max + 1)
    else:
        v = np.zeros_like(u, dtype=float)
        mask = u < u_max
        v[mask] = v_max * np.log((u_max + 1) / (u[mask] + 1)) / np.log(u_max + 1)
        return v

# Definerer parametre
v_max = 60  # km/t
u_max = 100  # biler/km

# Lager et array med tetthetsverdier
u_values = np.linspace(0, u_max, 400)

# Beregner hastighetene
v_values = v_log(u_values, v_max, u_max)


# Tegner grafen
plt.figure(figsize=(8, 6))
plt.plot(u_values, v_values)
plt.xlabel("Tetthet (u) [biler/km]")
plt.ylabel("Hastighet (v) [km/t]")
plt.title("Logaritmisk hastighetsfunksjon")
plt.grid(True)
plt.xlim(0, u_max)
plt.ylim(0, v_max * 1.1)
plt.show()
```

----

### Del 1, oppgave 1b-iii

Cosinusfunksjon

```python
# Plott av cosinus-funksjonen
import numpy as np
import matplotlib.pyplot as plt

def v_cos(u, v_max, u_max):
    """
    Cosinus-basert hastighetsfunksjon.

    Args:
        u: Tetthet (array eller enkeltverdi).
        v_max: Maksimal hastighet.
        u_max: Maksimal tetthet.

    Returns:
        Hastighet (array eller enkeltverdi).
    """
    return v_max * np.cos((np.pi / 2) * (u / u_max))

# Definerer parametre
v_max = 60  # km/t
u_max = 100  # biler/km

# Lager et array med tetthetsverdier
u_values = np.linspace(0, u_max, 400)

# Beregner hastighetene
v_values = v_cos(u_values, v_max, u_max)

# Tegner grafen
plt.figure(figsize=(8, 6))
plt.plot(u_values, v_values)
plt.xlabel("Tetthet (u) [biler/km]")
plt.ylabel("Hastighet (v) [km/t]")
plt.title("Cosinus-basert hastighetsfunksjon")
plt.grid(True)
plt.xlim(0, u_max)
plt.ylim(0, v_max * 1.1)
plt.show()
```

----

### Del 1, oppgave 2c



```python
# Python-kode for å animere $u(x,t)$ over tid ved bruk av Lax-Friedrichs metode
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.widgets import Slider

L = 1000  # banelengde
v_max = 28  # maksfart i m/s
Nx = 200  # antall punkt langs den diskretiserte x-aksen
Nt = 2000  # antall punkt langs den diskretiserte tidsaksen
dx = 2 * L / Nx  # delta x mellom hvert punkt
dt = 0.9 * (dx / v_max)  # delta t mellom hvert punkt
x = np.linspace(-L, L, Nx, dtype=np.float64)

u_max = 0.1  # maksimal tetthet i biler/m
u = np.zeros(Nx, dtype=np.float64)
u[x < 0] = u_max  # initialverdi

# inputvalidering for p
p = 0
valid_p = {1, 2, 5}
while p not in valid_p:
  p = int(input("Skriv inn ønsket p-verdi: "))

print(f"p = {p}")


# hastighetsfunksjon
def v(u):
  return v_max * np.power(1 - (u / u_max), p)


# fluksfunksjon
def J(u):
  return u * v(u)

#initialiser U
U = np.zeros((Nt, Nx), dtype=np.float64)
U[0, :] = u.copy()

# gjennomfør lax-friedrichs metode
for n in range(Nt - 1):
  J_u = J(u)
  for i in range(1, Nx - 1):
    U[n + 1, i] = 0.5 * (u[i - 1] + u[i + 1]) - (dt / (2 * dx)) * (
          J_u[i + 1] - J_u[i - 1])

  U[n + 1, -1] = 0
  for i in range(Nx):
    u[i] = U[n + 1, i]

# plotting og animering
fig, ax = plt.subplots(figsize=(8, 4))
plt.subplots_adjust(bottom=0.2)

line, = ax.plot(x, U[0], 'b')
ax.set_xlim(-L, L)
ax.set_ylim(0, u_max)
ax.set_xlabel('Posisjon x')
ax.set_ylabel('Tetthet u(x,t)')
title = ax.set_title(f'Trafikktetthet over tid for p = {p} (t = 0.00 s)')

ax_slider = plt.axes([0.2, 0.05, 0.65, 0.03])
slider = Slider(ax_slider, 'N_t', 0, Nt - 1, valinit=0, valstep=1)

def update(val):
  frame = int(slider.val)
  line.set_ydata(U[frame])
  title.set_text(
    f'Trafikktetthet over tid for p = {p} (t = {frame * dt:.2f} s)')
  fig.canvas.draw_idle()

slider.on_changed(update)
plt.show()
```

---

### Del 2, oppgave 3

```python
# Numerisk løsning av oppgave 3
import numpy as np
import matplotlib.pyplot as plt

antall = 1000
x_verdier = np.linspace(-1, 1, antall)
h = x_verdier[1] - x_verdier[0]
A = np.zeros((antall, antall))
b = np.cos(np.pi * x_verdier)

for i in range(1, antall - 1):
    A[i, i - 1] = 1 / h**2
    A[i, i] = -2 / h**2
    A[i, i + 1] = 1 / h**2
    
A[0, 0] = 1
b[0] = 0
A[-1, -1] = 1  
b[-1] = 2

numerisk_losning = np.linalg.solve(A, b)
analytisk_losning = -1/np.pi**2 * np.cos(np.pi * x_verdier) + x_verdier + 1 - 1/np.pi**2

plt.plot(x_verdier, numerisk_losning, 'ro-', markersize=2,  label="Numerisk løsning")
plt.plot(x_verdier, analytisk_losning, 'b-', label="Analytisk løsning")
plt.xlabel("x")
plt.ylabel("Funksjonsuttrykk")
plt.legend()
plt.grid(True)
plt.show()
\end{minted}
\begin{figure}[H]
    \centering
    \includegraphics[width=0.75\linewidth]{plott/Oppgave-3-ab.png}
    \caption{Sammenligning av A. og N. løsning}
\end{figure}
\cite{mathworks:solvepde}
\cite{mit:mckernan_lecture15}
\subsection{Oppgave 4: Varmeligning, 1D (5 poeng)}
\begin{minted}[frame=single, fontsize=\small]{python}
import numpy as np
import matplotlib.pyplot as plt

minimums_x, maximums_x = -1, 1
storste_t_verdi = 1.0  
antall_x = 100  
part_deriv_x = (maximums_x - minimums_x) / (antall_x - 1)
part_deriv_t = (0.4 * part_deriv_x**2)
t = int(np.ceil(storste_t_verdi / part_deriv_t))
x = np.linspace(minimums_x, maximums_x, antall_x)
liste_t_verdier = [0, 0.05, 0.075, 1]  # Ulike t - verdier for å vise temperaturfordeling over tid

#Varmeligningen
def f(x):
    return np.cos(np.pi * x)

#Startsbetingelsen
def start(x):
    return 1 + x + 5 * np.sin(np.pi * x)

#Rand-betingelsene
u = np.zeros((t + 1, antall_x))
u[0, :] = start(x)
u[:, 0] = 0  
u[:, -1] = 2  

for n in range(t):
    for i in range(1, antall_x - 1):
        u[n+1, i] = u[n, i] + part_deriv_t * ((u[n, i+1] - 2*u[n, i] + u[n, i-1]) / part_deriv_x**2 - f(x[i]))
    u[n+1, 0] = 0
    u[n+1, -1] = 2

#Lager en for loop for å kunne endre og teste ulike t verdier / 
for t_verdier in liste_t_verdier:
    t_tull = min(t, max(0, int(t_verdier / part_deriv_t)))
    plt.plot(x, u[t_tull, :], label=f't={t_verdier:.2f}')

plt.xlabel("x")
plt.ylabel("u(x,t)")
plt.legend()
plt.grid(True)
plt.show()
\end{minted}
\begin{figure}[H]
    \centering
    \includegraphics[width=0.75\linewidth]{plott/Oppgave 4 - Varmeligning.png}
    \caption{Varmeligningen med ulike T-verdier for å vise varmefordeling over tid}
\end{figure}
\cite{gitconnected:2d_heat_equation}
\subsection{Oppgave 5: Poissonligning, 2D (5 poeng)}
\begin{minted}[frame=single, fontsize=\small]{python}
import numpy as np
import matplotlib.pyplot as plt

antall_x, antall_y = 50, 20  
x = np.linspace(-5, 5, antall_x) 
y = np.linspace(0, 2, antall_y)  
X, Y = np.meshgrid(x, y)
u = np.zeros((antall_x, antall_y))

#Rand-betingelsene
u[0, :] = np.sin(2 * np.pi * y)   
u[-1, :] = np.sin(2 * np.pi * y)  
u[:, 0] = 0                       
u[:, -1] = np.sin(np.pi * x)      

for i in range(5000):  
    u[1:-1, 1:-1] = 0.25 * (u[:-2, 1:-1] + u[2:, 1:-1] + u[1:-1, :-2] + u[1:-1, 2:])
    
fig = plt.figure(figsize=(10, 6))
ax = fig.add_subplot(projection='3d')
ax.plot_surface(X, Y, u.T, cmap="cividis", edgecolor="none")
ax.set_xlabel("x")
ax.set_ylabel("y")
ax.set_zlabel("u(x, y)")
plt.show()
```

---

### Del 2, oppgave 4

```python
# Varmelikningen
import numpy as np
import matplotlib.pyplot as plt

minimums_x, maximums_x = -1, 1
storste_t_verdi = 1.0  
antall_x = 100  
part_deriv_x = (maximums_x - minimums_x) / (antall_x - 1)
part_deriv_t = (0.4 * part_deriv_x**2)
t = int(np.ceil(storste_t_verdi / part_deriv_t))
x = np.linspace(minimums_x, maximums_x, antall_x)
liste_t_verdier = [0, 0.05, 0.075, 1]  # Ulike t - verdier for å vise temperaturfordeling over tid

#Varmeligningen
def f(x):
    return np.cos(np.pi * x)

#Startsbetingelsen
def start(x):
    return 1 + x + 5 * np.sin(np.pi * x)

#Rand-betingelsene
u = np.zeros((t + 1, antall_x))
u[0, :] = start(x)
u[:, 0] = 0  
u[:, -1] = 2  

for n in range(t):
    for i in range(1, antall_x - 1):
        u[n+1, i] = u[n, i] + part_deriv_t * ((u[n, i+1] - 2*u[n, i] + u[n, i-1]) / part_deriv_x**2 - f(x[i]))
    u[n+1, 0] = 0
    u[n+1, -1] = 2

#Lager en for loop for å kunne endre og teste ulike t verdier / 
for t_verdier in liste_t_verdier:
    t_tull = min(t, max(0, int(t_verdier / part_deriv_t)))
    plt.plot(x, u[t_tull, :], label=f't={t_verdier:.2f}')

plt.xlabel("x")
plt.ylabel("u(x,t)")
plt.legend()
plt.grid(True)
plt.show()
```

----

### Del 2, oppgave 5

```python
# Poissonlikning
import numpy as np
import matplotlib.pyplot as plt

antall_x, antall_y = 50, 20  
x = np.linspace(-5, 5, antall_x) 
y = np.linspace(0, 2, antall_y)  
X, Y = np.meshgrid(x, y)
u = np.zeros((antall_x, antall_y))

#Rand-betingelsene
u[0, :] = np.sin(2 * np.pi * y)   
u[-1, :] = np.sin(2 * np.pi * y)  
u[:, 0] = 0                       
u[:, -1] = np.sin(np.pi * x)      

for i in range(5000):  
    u[1:-1, 1:-1] = 0.25 * (u[:-2, 1:-1] + u[2:, 1:-1] + u[1:-1, :-2] + u[1:-1, 2:])
    
fig = plt.figure(figsize=(10, 6))
ax = fig.add_subplot(projection='3d')
ax.plot_surface(X, Y, u.T, cmap="cividis", edgecolor="none")
ax.set_xlabel("x")
ax.set_ylabel("y")
ax.set_zlabel("u(x, y)")
plt.show()
```

---

### Del 2, oppgave 6b

```python
# Numerisk løsning
import numpy as np
import matplotlib.pyplot as plt
# Parametere
alpha = 1.172e-5  # Termisk diffusivitet for stål [m^2/s]
Lx, Ly = 0.4, 0.28  # Dimensjoner av legemet [m]
nx, ny = 40, 28  # Antall punkter i x- og y-retning

dx, dy = Lx / (nx), Ly / (ny)  # Romlig steglengde
nx, ny = int(Lx/dx), int(Ly/dy)
dt = dx**2 * dy**2 / (2 * alpha * (dx**2 + dy**2)) # Tidssteg

# Initialbetingelse
u = np.ones((nx, ny)) * 20  # Starttemperatur 20 grader



# Funksjon for å løse varmelikningen
def solve_heat_equation(u, alpha, dx, dy, dt, steps):
    for _ in range(steps):
        un = u.copy()  
        
        u[1:-1, 1:-1] = un[1:-1, 1:-1] + alpha * dt * (
            (un[2:, 1:-1] - 2 * un[1:-1, 1:-1] + un[:-2, 1:-1]) / dx**2 +
            (un[1:-1, 2:] - 2 * un[1:-1, 1:-1] + un[1:-1, :-2]) / dy**2
        )
        # Randbetingelser
        u[:, 0] = 200  # Venstre kant
        u[:, -1] = 200  # Høyre kant
        u[0, :] = 200  # Nedre kant
        u[-1, :] = 200  # Øvre kant
    return u

# Løsning og plotting ved ulike tidspunkt
times = [0, 1*60, 2*60, 3*60, 4*60, 5*60]  # Tid i sekunder
titles = ["0 minutter", "1 minutter", "2 minutter", "3 minutter", "4 minutter", "5 minutter"]
fig, axes = plt.subplots(3, 2, figsize=(10, 8))  # 3x2 grid av plott
fig.suptitle("Temperaturfordeling i legemet over tid", fontsize=16)

# Løsning og plotting ved ulike tidspunkt
for i, t in enumerate(times):
    steps = int(t / dt)  
    u = solve_heat_equation(u, alpha, dx, dy, dt, steps)
    
    # plotting 
    ax = axes[i // 2, i % 2]
    im = ax.imshow(u.T, cmap='bwr', vmin=20,vmax=200)
    ax.set_title(titles[i])
    ax.set_xlabel('x [m]')
    ax.set_ylabel('y [m]')
    

    fig.colorbar(im, ax=ax, label='Temperatur (°C)')

plt.tight_layout()
plt.show()	
```

---

### Del 2, oppgave 6c

```python
import numpy as np
import matplotlib.pyplot as plt

# Parametere
alpha = 1.172e-5  # Termisk diffusivitet for stål [m^2/s]
Lx, Ly = 0.4, 0.28  # Dimensjoner av legemet [m]
nx, ny = 40, 28  # Antall punkter i x- og y-retning
dx, dy = Lx / (nx ), Ly / (ny )  # Romlig steglengde
dt = dx**2 * dy**2 / (2 * alpha * (dx**2 + dy**2)) # Tidssteg 

# Initialbetingelse
u = np.ones((nx, ny)) * 20

# Funksjon for å løse varmeledningslikningen
def solve_heat_equation2(u, alpha, dx, dy, dt, steps):
    for _ in range(steps):
        un = u.copy()
        u[1:-1, 1:-1] = un[1:-1, 1:-1] + alpha * dt * (
            (un[2:, 1:-1] - 2 * un[1:-1, 1:-1] + un[:-2, 1:-1]) / dx**2 +
            (un[1:-1, 2:] - 2 * un[1:-1, 1:-1] + un[1:-1, :-2]) / dy**2
        )
        # Randbetingelser
        u[:, 0] = 200  # Venstre kant
        u[:, -1] = 200  # Høyre kant
        u[0, :] = 200  # Nedre kant
        u[-1, :] = 200  # Øvre kant
    return u

# Funksjon for å finne når midten når 60 grader
def find_time_to_reach_temp(u, alpha, dx, dy, dt, target_temp):
    steps = 0
    mid_x, mid_y = nx // 2, ny // 2  # Midten av legemet
    while u[mid_x, mid_y] < target_temp:
        steps += 1
        u = solve_heat_equation(u, alpha, dx, dy, dt, 1)  # Oppdater ett tidssteg
    return steps * dt

# Finn tiden
target_temp = 60
time_to_reach = find_time_to_reach_temp(u, alpha, dx, dy, dt, target_temp)
print(f"Det tar {time_to_reach/60:.1f} minutter å nå {target_temp}°C i midten.")

# Plot temperaturfordelingen ved dette tidspunktet
steps = int(time_to_reach / dt)
u = solve_heat_equation(u, alpha, dx, dy, dt, steps)
plt.figure()
plt.imshow(u.T, cmap='bwr', vmin=20,vmax=200)
plt.colorbar(label='Temperatur (°C)')
plt.title(f'Tid: {time_to_reach/60:.1f} minutter')
plt.xlabel('x [m]')
plt.ylabel('y [m]')
plt.show()
```

---

### Del 2, oppgave 6d

```python
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
%matplotlib notebook

# Parametere
alpha = 1.172e-5  # Termisk diffusivitet for stål [m^2/s]
Lx, Ly = 0.4, 0.28  # Dimensjoner av legemet [m]
nx, ny = 40, 28  # Antall punkter i x- og y-retning
dx, dy = Lx / (nx ), Ly / (ny )  # Romlig steglengde
dt = dx**2 * dy**2 / (2 * alpha * (dx**2 + dy**2)) # Tidssteg 

# Initialbetingelse
u = np.ones((nx, ny)) * 20

# Funksjon for å løse varmeledningslikningen
def solve_heat_equation(u, alpha, dx, dy, dt, steps):
    for _ in range(steps):
        un = u.copy()
        u[1:-1, 1:-1] = un[1:-1, 1:-1] + alpha * dt * (
            (un[2:, 1:-1] - 2 * un[1:-1, 1:-1] + un[:-2, 1:-1]) / dx**2 +
            (un[1:-1, 2:] - 2 * un[1:-1, 1:-1] + un[1:-1, :-2]) / dy**2
        )
        # Randbetingelser
        u[:, 0] = 200  # Venstre kant
        u[:, -1] = 200  # Høyre kant
        u[0, :] = 200  # Nedre kant
        u[-1, :] = 200  # Øvre kant
    return u

# Opprett figuren
fig, ax = plt.subplots()
im = ax.imshow(u.T, cmap='bwr',vmin=20,vmax=200)
plt.colorbar(im, label='Temperatur (°C)')
ax.set_xlabel('x [m]')
ax.set_ylabel('y [m]')
ax.set_title('Temperaturfordeling over tid')

# Funksjon for å oppdatere animasjonen
def update(frame):
    global u
    u = solve_heat_equation(u, alpha, dx, dy, dt, 10)  # Oppdater 10 tidssteg per frame
    im.set_array(u.T)  
    ax.set_title(f'Tid: {frame * dt * 10 / 60:.1f} minutter')
    return im,

# Lag animasjonen
ani = animation.FuncAnimation(fig, update, frames=100, interval=50, blit=True, repeat=False)
plt.show()
```

---

### Del 2, oppgave 7a

```python
import numpy as np
import matplotlib.pyplot as plt

# Parametere
alpha_steel = 1.172e-5  # Termisk diffusivitet for stål [m^2/s]
alpha_air = 2.0e-5  # Termisk diffusivitet for luft [m^2/s]

Lx, Ly = 0.8, 0.56  # Dimensjoner for hele ovnen (legeme + luft)
L_body_x, L_body_y = 0.4, 0.28  # Dimensjoner av selve legemet

dx, dy = 0.01, 0.01  # Romlig steglengde
nx, ny = int(Lx / dx), int(Ly / dy)

dt = dx**2 * dy**2 / (2 * alpha_air * (dx**2 + dy**2))  # Tidssteg

# Størrelse på legemet
body_x_start, body_x_end = int(0.2 / dx), int((0.2 + L_body_x) / dx)
body_y_start, body_y_end = int(0.14 / dy), int((0.14 + L_body_y) / dy)

# Temperaturmatrise
u = np.ones((nx, ny)) * 200
u[body_x_start:body_x_end, body_y_start:body_y_end] = 15  # Legemet starter på 15°C

alpha = np.ones((nx, ny)) * alpha_air
alpha[body_x_start:body_x_end, body_y_start:body_y_end] = alpha_steel

# Funksjon for å løse varmelikningen
def solve_heat_equation(u, alpha, dx, dy, dt, steps):
    for _ in range(steps):
        un = u.copy()
        u[1:-1, 1:-1] = un[1:-1, 1:-1] + alpha[1:-1, 1:-1] * dt * (
            (un[2:, 1:-1] - 2 * un[1:-1, 1:-1] + un[:-2, 1:-1]) / dx**2 +
            (un[1:-1, 2:] - 2 * un[1:-1, 1:-1] + un[1:-1, :-2]) / dy**2
        )
        # Randbetingelser
        u[:, 0] = 200  # Venstre kant
        u[:, -1] = 200  # Høyre kant
        u[0, :] = 200  # Nedre kant
        u[-1, :] = 200  # Øvre kant
    return u

# Løsning og plotting ved ulike tidspunkt

times = [0, 1*60, 2*60, 3*60, 4*60, 5*60]  # Tid i sekunder
titles = ["0 minutter", "1 minutter", "2 minutter", "3 minutter", "4 minutter", "5 minutter"]
fig, axes = plt.subplots(3, 2, figsize=(10, 8)) # 3x2 grid av plott
fig.suptitle("Temperaturfordeling i legemet og luftlaget", fontsize=16)

for i, t in enumerate(times):
    steps = int(t / dt)
    u = solve_heat_equation(u, alpha, dx, dy, dt, steps)
    
    ax = axes[i // 2, i % 2]
    im = ax.imshow(u.T, cmap='bwr', vmin=15, vmax=200)
    ax.set_title(titles[i])
    ax.set_xlabel('x [m]')
    ax.set_ylabel('y [m]')
    fig.colorbar(im, ax=ax, label='Temperatur (°C)')

plt.tight_layout()
plt.show()
```

---

### Del 2, oppgave 7b

```python
# Oppretter figuren
fig, ax = plt.subplots()
im = ax.imshow(u.T, cmap='bwr', vmin=15, vmax=200)
plt.colorbar(im, label='Temperatur (°C)')
ax.set_xlabel('x [m]')
ax.set_ylabel('y [m]')
ax.set_title('Temperaturfordeling over tid')

# Definerer animasjonen
def update(frame):
    global u
    u = solve_heat_equation(u, alpha, dx, dy, dt, 10)  # Oppdater 10 tidssteg per frame
    im.set_array(u.T)
    ax.set_title(f'Tid: {frame * dt * 10 / 60:.1f} minutter')
    return im,

# Lager animasjonen
ani = animation.FuncAnimation(fig, update, frames=100, interval=50, blit=True, repeat=False)
plt.show()
```

---

### Del 2, oppgave 7c

```python
import numpy as np
import matplotlib.pyplot as plt

# Funksjon for å finne når midten når 60 grader
def find_time_to_reach_temp(u, alpha, dx, dy, dt, target_temp, body_x_start, body_x_end, body_y_start, body_y_end):
    steps = 0
    mid_x, mid_y = (body_x_start + body_x_end) // 2, (body_y_start + body_y_end) // 2
    while u[mid_x, mid_y] < target_temp:
        steps += 1
        u = solve_heat_equation2(u, alpha, dx, dy, dt, 1)  # Oppdater ett tidssteg
    return steps * dt

# Finn tiden
target_temp = 60
time_to_reach = find_time_to_reach_temp(u, alpha, dx, dy, dt, target_temp, body_x_start, body_x_end, body_y_start, body_y_end)
print(f"Det tar {time_to_reach/60:.1f} minutter å nå {target_temp}°C i midten.")

# Plot temperaturfordelingen ved dette tidspunktet
steps = int(time_to_reach / dt)
u = solve_heat_equation2(u, alpha, dx, dy, dt, steps)
plt.figure()
plt.imshow(u.T, cmap='bwr', vmin=15, vmax=200)
plt.colorbar(label='Temperatur (°C)')
plt.title(f'Tid: {time_to_reach/60:.1f} minutter')
plt.xlabel('x [m]')
plt.ylabel('y [m]')
plt.show()
```

