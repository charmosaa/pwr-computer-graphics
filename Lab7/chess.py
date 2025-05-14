import pygame
from pygame.locals import *
from OpenGL.GL import *
from OpenGL.GLU import *
import math

def init_lighting():
    glEnable(GL_LIGHTING)
    
    # spotlight 0 - cold
    glLightfv(GL_LIGHT0, GL_POSITION, (-2.0, 4.0, -2.5, 1.0)) 
    glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, (0.3, -1.0, 0.0))  
    glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20.0) 
    glLightfv(GL_LIGHT0, GL_DIFFUSE, (0.5, 0.5, 0.7, 1.0))  # cold light
    glLightfv(GL_LIGHT0, GL_SPECULAR, (1.0, 1.0, 1.0, 1.0))
    glEnable(GL_LIGHT0)
    
    # spotlight 1 - warm
    glLightfv(GL_LIGHT1, GL_POSITION, (2, 5.0, 2, 1.0)) 
    glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, (-0.1, -1.0, 0))  
    glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 20.0)  
    glLightfv(GL_LIGHT1, GL_DIFFUSE, (0.9, 0.9, 0.7, 1.0))  # warm light
    glLightfv(GL_LIGHT1, GL_SPECULAR, (1.0, 1.0, 0.8, 1.0))
    glEnable(GL_LIGHT1)

    # spotlight 2 - color
    glLightfv(GL_LIGHT2, GL_POSITION, (-2.5, 3.0, 2.5, 1.0)) 
    glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, (-0.2, -1.0, 0.1))  
    glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 20.0)  
    glLightfv(GL_LIGHT2, GL_DIFFUSE, (0.9, 0.2, 0.2, 1.0))  # red light
    glLightfv(GL_LIGHT2, GL_SPECULAR, (1.0, 1.0, 0.8, 1.0))
    glEnable(GL_LIGHT2)
    
    # global light (ambient)
    glLightModelfv(GL_LIGHT_MODEL_AMBIENT, (0.07, 0.07, 0.07, 1.0))
    glEnable(GL_COLOR_MATERIAL)
    glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE)

def init():
    glEnable(GL_DEPTH_TEST)
    glClearColor(0.2, 0.2, 0.2, 1.0)
    glMatrixMode(GL_PROJECTION)
    gluPerspective(45, (800/600), 0.1, 50.0)

def draw_chessboard():
    glBegin(GL_QUADS)
    for i in range(8):
        for j in range(8):
            if (i + j) % 2 == 0:
                glColor3f(0.9, 0.9, 0.9)
            else:
                glColor3f(0.1, 0.1, 0.1)
            
            glMaterialfv(GL_FRONT, GL_SPECULAR, (0.3, 0.3, 0.3, 1.0))
            glMaterialf(GL_FRONT, GL_SHININESS, 20.0)
            glNormal3f(0, 1, 0)
            
            x, z = i - 4, j - 4
            glVertex3f(x, 0, z)
            glVertex3f(x + 1, 0, z)
            glVertex3f(x + 1, 0, z + 1)
            glVertex3f(x, 0, z + 1)
    glEnd()

def draw_pawn(quadric):
    """Draws a single pawn as a combination of transformed spheres"""
    glPushMatrix()

    # bottom - half sphere
    glPushMatrix()
    glTranslatef(0, 0.1, 0) 
    glScalef(1.5, 1, 1.5)  
    gluSphere(quadric, 0.2, 32, 32)
    glPopMatrix()

    # ring
    glPushMatrix()
    glTranslatef(0, 0.3, 0)  
    glScalef(1.3, 0.2, 1.3)  
    glEnable(GL_NORMALIZE)  # fixes normals after scaling
    gluSphere(quadric, 0.2, 32, 32)
    glDisable(GL_NORMALIZE)
    glPopMatrix()
    
    # middle - long part 
    glPushMatrix()
    glTranslatef(0, 0.3, 0)  
    glScalef(0.7, 2, 0.7)  
    gluSphere(quadric, 0.2, 32, 32)
    glPopMatrix()
    
    # upper ring
    glPushMatrix()
    glTranslatef(0, 0.6, 0)  
    glScalef(1, 0.2, 1)  
    glEnable(GL_NORMALIZE)  # fixes normals after scaling
    gluSphere(quadric, 0.2, 32, 32)
    glDisable(GL_NORMALIZE)
    glPopMatrix()
    
    # top - sphere
    glPushMatrix()
    glTranslatef(0, 0.8, 0)  
    glScalef(1, 1, 1)  
    gluSphere(quadric, 0.2, 32, 32)
    glPopMatrix()
        
    glPopMatrix()

def draw_pawns():
    """Draws all pawns on the chessboard"""
    quadric = gluNewQuadric()
    gluQuadricNormals(quadric, GLU_SMOOTH)
    
    # white pawn material
    glColor3f(0.9, 0.9, 0.9)
    glMaterialfv(GL_FRONT, GL_DIFFUSE, (0.7, 0.7, 0.7, 1.0))
    glMaterialfv(GL_FRONT, GL_SPECULAR, (0.7, 0.7, 0.7, 1.0))
    glMaterialf(GL_FRONT, GL_SHININESS, 5)
    
    # draw white pawns (row 1,2)
    for i in range(8):
        glPushMatrix()
        glTranslatef(i - 3.5, 0, -2.5)  # position the pawn on the board (x,z)
        draw_pawn(quadric)  
        glPopMatrix()

        glPushMatrix()
        glTranslatef(i - 3.5, 0, -3.5) 
        draw_pawn(quadric)  
        glPopMatrix()
    
    # black pawn material
    glColor3f(0.1, 0.1, 0.1)
    glMaterialfv(GL_FRONT, GL_DIFFUSE, (0.1, 0.1, 0.1, 1.0))
    glMaterialfv(GL_FRONT, GL_SPECULAR, (0.1, 0.1, 0.1, 1.0))
    glMaterialf(GL_FRONT, GL_SHININESS, 5)
    
    # draw black pawns (row 7,8)
    for i in range(8):
        glPushMatrix()
        glTranslatef(i - 3.5, 0, 2.5)  # position the pawn on the board (x,z)
        draw_pawn(quadric)  
        glPopMatrix()

        glPushMatrix()
        glTranslatef(i - 3.5, 0, 3.5) 
        draw_pawn(quadric)  
        glPopMatrix()
    
    gluDeleteQuadric(quadric)

def main():
    pygame.init()
    display = (800, 600)
    pygame.display.set_mode(display, DOUBLEBUF|OPENGL)
    
    init()
    
    clock = pygame.time.Clock()

    # for camera movement
    angle = 0
    camera_radius = 10   # distance from center of the board
    camera_height = 10     # height above the board
    
    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                return
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE:
                    pygame.quit()
                    return
        
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT)
        
        # update camera position 
        angle += 0.5
        rad = angle * math.pi / 180  
        
        # calculate camera position
        cam_x = camera_radius * math.cos(rad)
        cam_z = camera_radius * math.sin(rad)
        
        # Set up view matrix (camera)
        glMatrixMode(GL_MODELVIEW)
        glLoadIdentity()
        gluLookAt(
            cam_x, camera_height, cam_z,  # camera position
            0, 0, 0,                      # look at point (center)
            0, 1, 0                       # up vector
        )

        # draw scene 
        init_lighting()
        draw_chessboard()
        draw_pawns()
        
        pygame.display.flip()
        clock.tick(60)

if __name__ == "__main__":
    main()